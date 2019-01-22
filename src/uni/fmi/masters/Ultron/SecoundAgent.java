package uni.fmi.masters.Ultron;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import uni.fmi.masters.RoadOnto.RoadProtocolOntology;
import uni.fmi.masters.RoadOnto.Request;
import uni.fmi.masters.RoadOnto.Response;
import uni.fmi.masters.RoadOnto.RoadOntology;
import uni.fmi.masters.RoadOnto.Box;
import uni.fmi.masters.Ultron.RoadAgentGUI;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SecoundAgent extends Agent {

	private static final long serialVersionUID = 7954392838785044112L;

	private RoadAgentGUI gui;
	private boolean isFileOntologySet = false;
	private boolean isRegisteredInDF = false;
	private RoadOntology roadOntologyReader;
	
	public synchronized boolean setOntoFile(File f) {
		try {
			RoadOntology ro = new RoadOntology(f);
			 roadOntologyReader = ro;
			isFileOntologySet = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected void setup() {
		getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);	
		getContentManager().registerOntology(RoadProtocolOntology.getInstance());

		addBehaviour(new WakerBehaviour(this, 2000) {
			private static final long serialVersionUID = 64L;
			@Override
			protected void onWake() {
				gui = new RoadAgentGUI(SecoundAgent.this);
				gui.setVisible(true);				
			}
		});
		
		addBehaviour(new TickerBehaviour(this, 5000) {
			private static final long serialVersionUID = 5842325358797143755L;

			@Override
			protected void onTick() {
				try {
					if (isRegisteredInDF) {
						DFService.deregister(getAgent());
						isRegisteredInDF = false;
					}
					DFAgentDescription dfd = new DFAgentDescription();
					dfd.setName(getAgent().getAID());
					
					ServiceDescription sd = new ServiceDescription();
					sd.setType("road-info");
					sd.setName("road-service");
					sd.addOntologies(RoadProtocolOntology.NAME);
					sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
					
					dfd.addServices(sd);
					DFService.register(getAgent(), dfd);
					isRegisteredInDF = true;
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}						
			}
		});
		
		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 3737198530533327229L;
			private final MessageTemplate MT_REQUEST = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.and(
						MessageTemplate.MatchOntology(RoadProtocolOntology.NAME),
						MessageTemplate.MatchLanguage(FIPANames.ContentLanguage.FIPA_SL)
				)
			);
			
			@Override
			public void action() {
				ACLMessage msg = getAgent().receive(MT_REQUEST);
				if (msg == null) {
					block();
					return;
				}

				try {
					ContentElement ce = getAgent().getContentManager().extractContent(msg);
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					if (ce instanceof Action && ((Action)ce).getAction() instanceof Request && ((Request)((Action)ce).getAction()).hasBox()) {
						Box box = ((Request)((Action)ce).getAction()).getBox();
						if (box.hasId()) {
							Response resp = prepareResponse(box);
							if (resp != null) {
								//may be set action is not correct 
								((Action)ce).setAction((Concept) resp);
								reply.setPerformative(ACLMessage.INFORM);
								getContentManager().fillContent(reply, ce);
							}
						}
					}
					getAgent().send(reply);
				} catch (Exception e) {
					e.printStackTrace();
					block();
				}
			}
		});
	}
	
	protected void takeDown() {
		try {
			if (isRegisteredInDF) {
				DFService.deregister(this);
				isRegisteredInDF = false;
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private Response prepareResponse(Box request) {
		if (request == null || !request.hasId()) return null;

		Response r = new Response(new Box("", new ArrayList<>()));
		
		if (request.getId().isEmpty()) {
			r.getBox().setData(
					(List)roadOntologyReader.getMainClesses());
		} else {
			Box data = roadOntologyReader.getMoreInfo(request.getId());
			if (data.hasId() && data.hasData()) {
				Set<String> props = data.getData().stream().map(o -> {
					Box v = (Box)o;
					return v.getId();
				}).collect(Collectors.toSet());
				
				if (request.hasData()) {
					request.getData().forEach(d -> props.remove(d));
					
					for (Iterator<Object> it = data.getData().iterator(); it.hasNext(); ) {
						Box d = (Box) it.next();
						if (props.contains(d.getId())) {
							it.remove();
						}
					}
				}
			}
			r.setBox(data);
		}

		return r;
	}
	

}


