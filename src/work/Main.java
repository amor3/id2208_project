package work;

import java.util.HashMap;
import java.util.Map;
import ontology.MyOntManager;
import org.mindswap.pellet.owlapi.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

/**
 *
 * @author AMore Johan
 */
public class Main {

    private final String ontologyLocation = "file:///Users/AMore/NetBeansProjects/id2208_project/data/SUMO.owl";
    private MyOntManager myOntManager;
    private OWLOntologyManager owlOntologyManager;
    private OWLOntology owlOntology;
    private Reasoner reasoner;
    private HashMap<String, OWLClass> owlMap;
    
    public Main() {
        myOntManager = new MyOntManager();
        owlOntologyManager = myOntManager.initializeOntologyManager();
        owlOntology = myOntManager.initializeOntology(owlOntologyManager, ontologyLocation);
        reasoner = myOntManager.initializeReasoner(owlOntology, owlOntologyManager);
        
        owlMap = myOntManager.loadClasses(reasoner);
    }

    public void printClasses(Map<String, OWLClass> map){
        for(Map.Entry<String, OWLClass> entry : owlMap.entrySet()){
            System.out.println("KEY: " + entry.getKey() + ", VALUE: " + entry.getValue());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String... args) {
        Main main = new Main();

        main.printClasses(null);
    }

}
