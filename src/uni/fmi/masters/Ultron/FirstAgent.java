package uni.fmi.masters.Ultron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import java.util.stream.Collectors;

import uni.fmi.masters.RoadOnto.RoadProtocolOntology;
import uni.fmi.masters.RoadOnto.Request;
import uni.fmi.masters.RoadOnto.Response;
import uni.fmi.masters.RoadOnto.Box;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
/**
 * 
 * @author Georgi Kostov, Georgi Gyulev
 * @version v2
 *
 */
public class FirstAgent extends Agent {
	
 Box v = new Box();





public FirstAgent() {
		super();
		//messenger = new MessengerBox();
		
		messenger = new TestWindow();
		
	}
private static final long serialVersionUID = 3L;



private AID agentRoad = null;

/**
 * <div>Set up the first agent so that can perform and action</div>
 * <div>Adding new behavior with duration.</div>
 */

TestWindow messenger;


@Override
public void setup() {
	System.out.println("Hello!");
	//messenger = new MessengerBox();

	addBehaviour(new WakerBehaviour(this, 1000) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public void onWake() {
			//messenger.setVisible(true);
			messenger.frmChoseTheObject.setVisible(true);
		}
	});
	
	getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);	
	getContentManager().registerOntology(RoadProtocolOntology.getInstance());	
	
	addBehaviour(new TickerBehaviour(this, 5000) {
		private static final long serialVersionUID = 7L;
		
		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
		
			if(messenger.takeAction()==false) {
			
				block();
				return;
			}
			
			messenger.createAction(false);
			
			agentRoad = searchInYellowPages();

				addBehaviour(new OneShotBehaviour(getAgent()) {
					private static final long serialVersionUID = 17L;
/**
 * @this is the action that take place when searching in yellow pages .
 */
					@Override
					public void action() {							
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
						msg.setOntology(RoadProtocolOntology.NAME);
						msg.addReceiver(agentRoad);
						
						Request req = new Request(new Box("", null));
						Action act = new Action(agentRoad, req);
						try {
							getContentManager().fillContent(msg, act);
						} catch (CodecException | OntologyException e) {
							e.printStackTrace();
						}
						getAgent().send(msg);
						
						final MessageTemplate MT_RESP = MessageTemplate.and(
									MessageTemplate.MatchPerformative(ACLMessage.INFORM),
									MessageTemplate.and(
									MessageTemplate.MatchOntology(RoadProtocolOntology.NAME),
									MessageTemplate.MatchLanguage(FIPANames.ContentLanguage.FIPA_SL)
							)
						);
						
						ACLMessage resp = getAgent().blockingReceive(MT_RESP);
						if (resp == null) { block(); return; }
						
						try {
							ContentElement ce = getAgent().getContentManager().extractContent(resp);
							if (ce instanceof Action && ((Action)ce).getAction() instanceof Response 
									&& ((Response)((Action)ce).getAction()).hasBox()) {
								Box v = ((Response)((Action)ce).getAction()).getBox();
								System.out.println(v.toString());
								
								//// get some road object property values - hardcoded
								
								Request r = new Request(new Box(messenger.getFieldValue() , new ArrayList<>()));
								
								//r.getBox().getData().add("IsNotLivingThing"); // filtering
								//r.getBox().getData().add(" "); // filtering
								act = new Action(agentRoad, r);
								try {
									getContentManager().fillContent(msg, act);
								} catch (CodecException | OntologyException e) {
									e.printStackTrace();
								}
								getAgent().send(msg);
								
								resp = getAgent().blockingReceive(MT_RESP);
								if (resp == null) { block(); return; }
								ce = getAgent().getContentManager().extractContent(resp);
								if (ce instanceof Action && ((Action)ce).getAction() instanceof Response 
										&& ((Response)((Action)ce).getAction()).hasBox()) {
									v = ((Response)((Action)ce).getAction()).getBox();
									
									List<String> c = v.getData().stream().map(e->{
										Box box = (Box) e;
										return box.getId().toString();
									}).collect(Collectors.toList());
									//System.out.println(v.toString());
									String text = String.join(", ", c);
									messenger.setTPF(text);

									List<Object> getDatas =((List<Object>) v.getData());
									String text2 = "";
									HashSet<String> classes= new HashSet<String>();
									
									for(Object b : getDatas ) {
										Box box = (Box)b;
										for(Object innerBox : box.getData()) {
											classes.add((String)innerBox);
										}
									}
									text2 = String.join(", ", classes);
									messenger.setTOF(text2);
									
									
								}
							} else {
								System.out.println("Invalid request!!");
								block();
							}								
						} catch (CodecException | OntologyException e) {
							e.printStackTrace();
							block();
						}
					;}
				});
			}
	});
	
	getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);	
	// when create indulge ---getContentManager().registerOntology(PizzaProtocolOntology.getInstance());	
	/**
	 * 
	 * Adding new behavior  with duration.
	 */
	/*addBehaviour(new TickerBehaviour(this,  5000) {
		
		@Override
		public void onTick() {
			agentRoad = searchInYellowPages();
			if (agentRoad != null) {
				removeBehaviour(this);

				addBehaviour(new OneShotBehaviour(getAgent()) {
					private static final long serialVersionUID = 17L;
/**
 * @this is the action that take place when serachin in yellow pages .
 */
					/*@Override
					public void action() {							
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
						msg.setOntology(RoadProtocolOntology.NAME);
						msg.addReceiver(agentRoad);
						
						Action act = new Action(agentRoad, new Request(new Box("", null)));
						try {
							getContentManager().fillContent(msg, act);
						} catch (CodecException | OntologyException e) {
							e.printStackTrace();
						}
						getAgent().send(msg);
						
						final MessageTemplate MT_RESP = MessageTemplate.and(
									MessageTemplate.MatchPerformative(ACLMessage.INFORM),
									MessageTemplate.and(
									MessageTemplate.MatchOntology(RoadProtocolOntology.NAME),
									MessageTemplate.MatchLanguage(FIPANames.ContentLanguage.FIPA_SL)
							)
						);
						
						ACLMessage resp = getAgent().blockingReceive(MT_RESP);
						if (resp == null) { block(); return; }
						
						try {
							ContentElement ce = getAgent().getContentManager().extractContent(resp);
							if (ce instanceof Action && ((Action)ce).getAction() instanceof Response 
									&& ((Response)((Action)ce).getAction()).hasBox()) {
								Box v = ((Response)((Action)ce).getAction()).getBox();
								System.out.println(v.toString());
								
								//// get some road object property values - hardcoded
								Request r = new Request(new Box("Car", new ArrayList<>()));
								r.getBox().getData().add("IsNotLivingThing"); // filtering
								r.getBox().getData().add(" "); // filtering
								act = new Action(agentRoad, r);
								try {
									getContentManager().fillContent(msg, act);
								} catch (CodecException | OntologyException e) {
									e.printStackTrace();
								}
								getAgent().send(msg);
								
								resp = getAgent().blockingReceive(MT_RESP);
								if (resp == null) { block(); return; }
								ce = getAgent().getContentManager().extractContent(resp);
								if (ce instanceof Action && ((Action)ce).getAction() instanceof Response 
										&& ((Response)((Action)ce).getAction()).hasBox()) {
									v = ((Response)((Action)ce).getAction()).getBox();
									System.out.println(v.toString());
								}
							} else {
								System.out.println("Invalid request!!");
								block();
							}								
						} catch (CodecException | OntologyException e) {
							e.printStackTrace();
							block();
						}
					}
				});
			}
			
		}
	});
	*/
	
	
	
}
/**
 * 
 * @return Retun the agentRoad after it search for it on YellowPages.
 * <b> also this is a test ...so dont judje to harsh ;) </b>
 */

	private AID searchInYellowPages() {
		AID agent = null;
		
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription templateSd = new ServiceDescription();
			templateSd.setType("road-info");
			template.addServices(templateSd);
			
			try {
			DFAgentDescription[] results = DFService.search(this, template);
			if (results != null && results.length > 0) {
				// shuffle results to pick a random agent;
				for (int i = 0; i < results.length; ++i) {
					int j = ThreadLocalRandom.current().nextInt(0, results.length);
					DFAgentDescription temp = results[i];
					results[i] = results[j];
					results[j] = temp;
				}
				agent = results[ThreadLocalRandom.current().nextInt(0, results.length)].getName();
			}
		} catch (FIPAException e) {
			e.printStackTrace(System.err);
		}
			
			return agent;
	}

}
