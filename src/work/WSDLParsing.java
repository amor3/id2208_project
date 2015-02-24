package work;

import com.ibm.wsdl.OperationImpl;
import com.ibm.wsdl.PartImpl;
import com.ibm.wsdl.PortTypeImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Definition;
import javax.wsdl.Types;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

/**
 *
 * @author AMore Johan
 */
public class WSDLParsing {

    private Types types;
    private Map messages;
    private Map portTypes;
    private Map bindings;
    private Map services;
    private Definition definition;
    private WSDLReader reader;
    private WSDLFactory factory;

    public WSDLParsing(String filePath) {
        try {
            factory = WSDLFactory.newInstance();
            reader = factory.newWSDLReader();
            definition = reader.readWSDL(null, filePath);
        } catch (Exception ex) {
            Logger.getLogger(WSDLParsing.class.getName()).log(Level.SEVERE, null, ex);
        }

        types = definition.getTypes();
        messages = definition.getMessages();
        portTypes = definition.getPortTypes();
        bindings = definition.getBindings();
        services = definition.getServices();
    }

    public String getWSName() {
        String s = "";
        Iterator it = services.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            s = ((QName) pairs.getKey()).getLocalPart();
        }
        return s;
    }

    public void getTypes() {
        
        ArrayList<QName> typeNames = new ArrayList<>();
        Map parts = getOperations().get(0).getOutput().getMessage().getParts();
        Iterator it2 = parts.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pairs2 = (Map.Entry) it2.next();
            PartImpl part = (PartImpl) pairs2.getValue();
            QName elementName = part.getTypeName();
            if (elementName != null) {
                typeNames.add(elementName);
                System.out.println(elementName);
            }
        }
    }

    public List<OperationImpl> getOperations() {

        List<OperationImpl> operations = new ArrayList<>();
        Iterator it = portTypes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            PortTypeImpl pti = (PortTypeImpl) pairs.getValue();
            operations = (List<OperationImpl>) pti.getOperations();
        }
        return operations;
    }

    public ArrayList<QName> getOutputElements(OperationImpl op) {
        ArrayList<QName> enames = new ArrayList<>();
        Map parts = op.getOutput().getMessage().getParts();
        Iterator it2 = parts.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pairs2 = (Map.Entry) it2.next();
            PartImpl part = (PartImpl) pairs2.getValue();
            QName elementName = part.getElementName();
            if (elementName != null) {
                enames.add(elementName);
            }
        }
        return enames;
    }

    /*
     public Map<String> getPortTypes() {

     }

     public List<Operation> getOperations() {

     }

     public void getOutputElements() {

     }

     public void getInputElements() {

     }
     */
    public static void main(String... args) {
        WSDLParsing p = new WSDLParsing("file:///Users/AMore/NetBeansProjects/id2208_project/src/wsdl/FlightwiseAPIProfile.wsdl");
        p.getTypes();
    }
}
