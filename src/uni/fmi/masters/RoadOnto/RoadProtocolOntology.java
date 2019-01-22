package uni.fmi.masters.RoadOnto;



import jade.content.onto.BasicOntology;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;

public class RoadProtocolOntology extends BeanOntology {
	private static final long serialVersionUID = 62L;
	public static final String NAME = "road-protocol-ontology";

	private static Ontology theInstance = new RoadProtocolOntology();
	
	
	public RoadProtocolOntology() {
		super(NAME, BasicOntology.getInstance());
		// TODO Auto-generated constructor stub
		
		try {
			add(this.getClass().getPackage().getName());
		} catch (BeanOntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Ontology getInstance() {
		return theInstance;
	}
	

}
