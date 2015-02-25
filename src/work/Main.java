package work;

import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.PortType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;
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
    
    
    
    
    public static void exportToFile(WSMatchingType wSMatchingType) {
        try {
            File outputFile = new File("output.xml");
            JAXBContext context = JAXBContext.newInstance(
                wSMatchingType.getClass().getPackage().getName());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            JAXBElement<WSMatchingType> topLevelElement = new JAXBElement<>(
                new QName("ns2:WSMatching"), 
                WSMatchingType.class, wSMatchingType
            );
            marshaller.marshal(topLevelElement, outputFile);
        } catch (JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static void main(String... args){
        /*
        WSDLParsing wsdlParsing = new WSDLParsing("file:///Users/AMore/NetBeansProjects/id2208_project/src/wsdl/PaymentsGatewayAPIProfile.wsdl");
        
          
        System.out.println("__________________");
        for(String sname : wsdlParsing.getServiceNames()){
            System.out.println("ServiceName: " + sname);
        }
        
        System.out.println("__________________");
        for(PortType pt : wsdlParsing.getPortTypes()){
            System.out.println("GetPortTypes: " + pt.getName());
        }
        
        
        System.out.println("__________________");
        for(Operation op : wsdlParsing.getOperations()){
            System.out.println("GetOperation: " + op.getName());
        }
        
        
        System.out.println("__________________");
        for(Message msg : wsdlParsing.getOutputElements()){
            System.out.println("OutPutElements: " + msg.getName());
        }

        
        System.out.println("__________________");
        for(Message msg : wsdlParsing.getInputElements()){
            System.out.println("InPutElements: " + msg.getName());
        }
        
        System.out.println("__________________");
        for(String type : wsdlParsing.findTypesByMessageName("ITransactionService_getReceivedSummary_OutputMessage")){
            System.out.println("Type: " + type);
        }
        
        
        System.out.println("__________________");
        for(String type : wsdlParsing.findInputTypesByOperationName("searchTransactions")){
            System.out.println("InputType for " + "SendMoney: " + type);
        }
             
        */
        
        
        MainMatcher mm = new MainMatcher(
            "file:///Users/johanand/NetBeansProjects/id2208_project/src/wsdl/drink2.wsdl", 
            "file:///Users/johanand/NetBeansProjects/id2208_project/src/wsdl/wine2.wsdl"
        );
        WSMatchingType wSMatchingType = mm.match();
        Main.exportToFile(wSMatchingType);

//FlightPlansSoapIn
      
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    
    public static void main(String... args) {
        Main main = new Main();

        main.printClasses(null);
    }
    
    */

}
