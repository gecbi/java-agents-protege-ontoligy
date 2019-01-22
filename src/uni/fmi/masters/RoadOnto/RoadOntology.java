package uni.fmi.masters.RoadOnto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import uni.fmi.masters.RoadOnto.Box;

public class RoadOntology {
	
private OWLOntologyManager manager = null;
private OWLOntology ontology = null; 
private OWLDataFactory factory = OWLManager.getOWLDataFactory();


private File ontoFile = null;

public RoadOntology (File f ) throws OWLOntologyCreationException {
	
	ontoFile = f;
	manager = OWLManager.createOWLOntologyManager();
	ontology = manager.loadOntologyFromOntologyDocument(ontoFile);
	factory = manager.getOWLDataFactory();
	
	System.out.println(" Loaded ontology : " + ontology.getOntologyID());
	 

}

public List<Box> getMainClesses(){
	List<Box> boxData = new ArrayList<>();
	
	Set<OWLClass> objectClasses = ontology.getClassesInSignature(); 
	
	Box box;
	for(OWLClass cls:objectClasses ) {
		if(!isObject(cls))
			continue;
		
		box = new Box();
		
		String classID = cls.getIRI().toString();
		box.setId(classID.substring(classID.indexOf("#")+1));
		
		Set<OWLAxiom> axioms = cls.getReferencingAxioms(ontology);
		
		List<Object> boxProperty = new ArrayList<Object>();
		
		
		for(OWLAxiom axi: axioms) {
			String propertyName = axi.getObjectPropertiesInSignature().toString();
			
			propertyName = propertyName.substring(propertyName.indexOf("#") +1, propertyName.length()-2);
			
			if(!propertyName.equals("")&& !boxProperty.contains(propertyName)) {
				boxProperty.add(propertyName);
			}
			
		}
		box.setData(boxProperty);
		boxData.add(box);
		
		
		
	}
	
	return boxData;
	
}


public Box getMoreInfo(String classID) {
	
	Box box = new Box();
	
	for (OWLClass cls: ontology.getClassesInSignature()) {
		if(cls.getIRI().toString().contains(classID)) {
			box.setId(classID);
			List<Object> boxData = new ArrayList<Object>();
			
			Set<OWLAxiom> axioms = cls.getReferencingAxioms(ontology);
			
			for(OWLAxiom axi: axioms) {
				
				if(axi.getAxiomType().getName().contains("SubClassOf")){
					
					//get property
					Set<OWLObjectProperty> ops = axi.getObjectPropertiesInSignature();
					for(OWLObjectProperty op : ops)
					{
						//get clesses
						Set<OWLClass> axiomClasses =  axi.getClassesInSignature();
						for(OWLClass cl: axiomClasses)
						{
							String className = cl.getIRI().toString();
							className = className.substring(className.indexOf('#') + 1);
							
							if(!className.equals(classID)){
							
								String prop = op.getNamedProperty().getIRI().toString();
								prop = prop.substring(prop.indexOf('#') + 1);									
								
								// split on "prop name" : [values, ... ], .....
								final String prop2 = prop; 
								final String className2 = className;
								Box v = (Box)boxData.stream()
									.filter(vd -> vd instanceof Box && prop2.equals(((Box)vd).getId()))
									.findFirst()
									.orElse(null);
								if (v == null) {
									boxData.add(v = new Box(prop, new ArrayList<Object>() {
										/**
										 * 
										 */
										private static final long serialVersionUID = 1L;

									{ add(className2); }}));
								} else {
									v.getData().add(className2);
								}
							}
						}
						
						//get individuals
						Set<OWLNamedIndividual> indivs = axi.getIndividualsInSignature();
						for(OWLNamedIndividual indv: indivs)
						{
							String indvName = indv.getIRI().toString();
							indvName = indvName.substring(indvName.indexOf('#') + 1);
						
							String prop = op.getNamedProperty().getIRI().toString();
							prop = prop.substring(prop.indexOf('#') + 1);									
								
							// split on "prop name" : [values, ... ], .....
							final String prop2 = prop;
							final String indvName2 = indvName;
							Box v = (Box)boxData.stream()
								.filter(vd -> vd instanceof Box && prop2.equals(((Box)vd).getId()))
								.findFirst()
								.orElse(null);
							if (v == null) {
								boxData.add(v = new Box(prop, new ArrayList<Object>() {{ add(indvName2); }}));
							} else {
								v.getData().add(indvName2);
							}
						}
						
											
					}
					
				}
			
			
				
			}
			
			box.setData(boxData);
		}
		
	}
	
	
	return box;

}


private boolean isObject(OWLClass owlClass)
{
	
	Set<OWLClassExpression> superClass = owlClass.getSuperClasses(ontology);
	for(OWLClassExpression expr : superClass)
	{
		for(OWLClass cls: expr.getClassesInSignature()){
			String className = cls.getIRI().toString();
	
			if(className.substring(className.indexOf('#')+1).equals(" ")){
				
				return true;
			}
		}
	}
	
	return false;

}

}
