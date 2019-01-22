package uni.fmi.masters.Ultron;

import org.semanticweb.owlapi.model.OWLOntologyManager;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

public class Go {

	private String ontologyFileString = "";
	private String ontologyIRIString = "http://www.semanticweb.org/georg/ontologies/2018/1/Ultron-v2.owl";
	
	public void loadIRI() {
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		IRI iri = IRI.create("C://Users//georg//eclipse-workspace//Ultron//resourses//Ultron-v2.owl");
		
		OWLOntology roadOntology;
		
		try {
			roadOntology = manager.loadOntologyFromOntologyDocument(iri);
			
			System.out.println("Loaded ontology form IRI as follow :" + roadOntology);
			
			manager.removeOntology(roadOntology);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
	}
	
	public void laodFromFile () {
		
		OWLOntology localRoad;
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		File file = new File(ontologyFileString);
		
		try {
			localRoad = manager.loadOntologyFromOntologyDocument(file);
			
			System.out.println("Loaded ontology from file: " + localRoad);
			IRI documentIRI = manager.getOntologyDocumentIRI(localRoad);
			 System.out.println("    from: " + documentIRI);
			
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		
	}
	
	public void addSubClassOfAxioms (){
		
		try {
			//create ontology manager and data factory
			OWLOntologyManager manager =  OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		
			File file = new File(ontologyFileString);
			
			//load ontology from file
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
			
			//create IRI of our road ontology. NOTE: this IRI must be the same as the one showed in Protege
			//under Active Ontology tab
			IRI pizzaIRI = IRI.create(ontologyFileString);

			//get the our Object class
			OWLClass clsObject = dataFactory.getOWLClass(IRI.create(pizzaIRI + "#Object"));;
		
			//create class LivingThing
			OWLClass clsLivingThing = dataFactory.getOWLClass(IRI.create(pizzaIRI + "#LivingThing"));
		
			//create the axiom
			OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(clsLivingThing, clsObject);
		
			//add the axiom to the ontology.
			AddAxiom addAxiom = new AddAxiom(ontology, axiom);
		
			//use the manager to apply the change
			manager.applyChange(addAxiom);

			//save changes to the ontology
			manager.saveOntology(ontology);
		
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void removeAxiom (){
		 try {
			 
		 	//create ontology manager and data factory
			OWLOntologyManager manager =  OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		
			File file = new File(ontologyIRIString);
			
			//load ontology from file
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
			
			//create IRI of our road ontology. NOTE: this IRI must be the same as the one showed in Protege
			//under Active Ontology tab
			IRI roadIRI = IRI.create(ontologyIRIString);

			//get the our Object class
			OWLClass clsObject = dataFactory.getOWLClass(IRI.create(roadIRI + "#Object"));;
			
			//create class LivingThing
			OWLClass clsLivingThing = dataFactory.getOWLClass(IRI.create(roadIRI + "#LivingThing"));
		
			//create the axiom
			OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(clsLivingThing, clsObject);
		
			RemoveAxiom removeAxiom = new RemoveAxiom(ontology,axiom);
			manager.applyChange(removeAxiom);
			
			//save changes to the ontology
			manager.saveOntology(ontology);
		
				manager.saveOntology(ontology);
			} catch (OWLOntologyStorageException | OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	 }
	
	public void addSomeValuesFromRestriction (){
		
		try {
			//create ontology manager and data factory
			OWLOntologyManager manager =  OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		
			File file = new File(ontologyFileString);
			
			//load ontology from file
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
			
			//create IRI of our road ontology. NOTE: this IRI must be the same as the one showed in Protege
			//under Active Ontology tab
			IRI roadIRI = IRI.create(ontologyIRIString);//(ontologyIRIString);

			//get the our Object class
			OWLClass clsObject = dataFactory.getOWLClass(IRI.create(roadIRI + "#LivingThing"));;
		
			//get class LivingThing
			OWLClass clsLivingThing = dataFactory.getOWLClass(IRI.create(roadIRI + "#Cow"));
		
			//get property isLivingThing
			OWLObjectProperty isLivingThing = dataFactory.getOWLObjectProperty(IRI.create(roadIRI + "#isLivingThing"));
			
			//create restriction that describe class of individuals that is alive and moving
			OWLClassExpression isMoving = dataFactory.getOWLObjectSomeValuesFrom(isLivingThing, clsObject);
			
			//create the axiom
			OWLSubClassOfAxiom axiom = dataFactory.getOWLSubClassOfAxiom(clsObject, isMoving);
		
			//add the axiom to the ontology
			AddAxiom addAxiom = new AddAxiom(ontology, axiom);
		
			//use the manager to apply the change
			manager.applyChange(addAxiom);

			//save changes to the ontology
			manager.saveOntology(ontology);
		
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeClass(){
		
		try{
	 	//create ontology manager and data factory
		OWLOntologyManager manager =  OWLManager.createOWLOntologyManager();
		OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
	
		File file = new File(ontologyFileString);
		
		//load ontology from file
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);		
		
		//create remover
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(ontology));
				
		//visit all classes 
		for (OWLClass cls: ontology.getClassesInSignature()){
			//if the IRI of our newly created class is living thing
			if(cls.getIRI().toString().contains("#LivingThing"))
				//delete it
				cls.accept(remover);
			}
				
		manager.applyChanges(remover.getChanges());

		//save changes to the ontology
		manager.saveOntology(ontology);
			
		}catch (OWLOntologyCreationException | OWLOntologyStorageException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	//reading from ontology
	public void getAnnotationLabelsOfClassAxiom(){
		try{
		 	//create ontology manager and data factory
			OWLOntologyManager manager =  OWLManager.createOWLOntologyManager();
			OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();
		
			File file = new File(ontologyFileString);
			
			//load ontology from file
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);		
			
			//walk through all classes
			for (OWLClass cls : ontology.getClassesInSignature()) {
				
				for(OWLSubClassOfAxiom axiom : ontology.getSubClassAxiomsForSubClass(cls)){
					if(axiom instanceof OWLSubClassOfAxiom){

						for(OWLAnnotation annotation: axiom.getAnnotations()){
							
							//get only these annotations that are literals 
							if (annotation.getValue() instanceof OWLLiteral) {
								OWLLiteral val = (OWLLiteral) annotation.getValue();
										System.out.println(cls + " Label: " + val.getLiteral());
							}
						}
					}
				}
				
			}
		}catch (OWLOntologyCreationException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
