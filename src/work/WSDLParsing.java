package work;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AMore Johan
 */
public class WSDLParsing {

    private final String filePath;
    private final Definitions defs;
    private final WSDLParser parser = new WSDLParser();

    public WSDLParsing(String filePath) {
        this.filePath = filePath;
        this.defs = parser.parse(filePath);
    }

    public List<PortType> getPortTypes() {
        return defs.getPortTypes();
    }

    public List<String> getServiceNames() {
        ArrayList<String> serviceArray = new ArrayList<>();
        for (Service service : defs.getServices()) {
            serviceArray.add(service.getName());
        }
        return serviceArray;
    }

    public List<Operation> getOperations() {
        ArrayList<Operation> opList = new ArrayList<>();

        for (PortType pt : getPortTypes()) {
            for (Operation op : pt.getOperations()) {
                opList.add(op);
            }
        }
        return opList;
    }

    public List<Message> getOutputElements() {
        ArrayList<Message> msgArray = new ArrayList<>();

        for (PortType pt : getPortTypes()) {
            for (int i = 0; i < pt.getOperations().size(); i++) {
                msgArray.add(pt.getOperations().get(i).getOutput().getMessage());
            }
        }
        return msgArray;
    }

    public List<Message> getInputElements() {
        ArrayList<Message> msgArray = new ArrayList<>();
        for (PortType pt : getPortTypes()) {
            for (int i = 0; i < pt.getOperations().size(); i++) {
                msgArray.add(pt.getOperations().get(i).getInput().getMessage());
            }
        }
        return msgArray;
    }

    public List<String> findTypesByMessageName(String elementName) {
        ArrayList<String> typesArray = new ArrayList<>();

        for (Message msg : defs.getMessages()) {
            if (msg.getName().equals(elementName)) {
                for (Part part : msg.getParts()) {
                    typesArray.add(part.getName());
                }
            }
        }
        return typesArray;
    }

    public List<String> findInputTypesByOperationName(String operationName) {
        ArrayList<String> typesArray = new ArrayList<>();

        for (PortType pt : getPortTypes()) {
            for (Operation op : pt.getOperations()) {
                if (op.getName().equals(operationName)) {
                    for (Part part : op.getInput().getMessage().getParts()) {

                        if (part.getElement() != null) {

                            Pattern p = Pattern.compile("name=(['])(?:(?=(\\\\?))\\2.)*?\\1");
                            Matcher m = p.matcher(part.getElement().getAsString());

                            while (m.find()) {
                                if (!typesArray.contains(m.group().replace("'", "").replace("name=", ""))
                                        && !m.group().replace("'", "").replace("name=", "").equals(op.getInput().getMessage().getName())) {

                                    typesArray.add(m.group().replace("'", "").replace("name=", ""));
                                }
                            }
                        } else {
                            if (!typesArray.contains(part.getName()) && !part.getName().equals(op.getInput().getMessage().getName())) {
                                typesArray.add(part.getName());
                            }
                        }

                    }
                }
            }
        }
        return typesArray;
    }

    public List<String> findOutputTypesByOperationName(String operationName) {
        ArrayList<String> typesArray = new ArrayList<>();

        for (PortType pt : getPortTypes()) {
            for (Operation op : pt.getOperations()) {
                if (op.getName().equals(operationName)) {
                    for (Part part : op.getOutput().getMessage().getParts()) {
                        if (part.getElement() != null) {

                            Pattern p = Pattern.compile("name=(['])(?:(?=(\\\\?))\\2.)*?\\1");
                            Matcher m = p.matcher(part.getElement().getAsString());

                            while (m.find()) {
                                if (!typesArray.contains(m.group().replace("'", "").replace("name=", ""))
                                        && !m.group().replace("'", "").replace("name=", "").equals(op.getOutput().getMessage().getName())) {
                                    typesArray.add(m.group().replace("'", "").replace("name=", ""));
                                }
                            }
                        } else {
                            if (!typesArray.contains(part.getName()) && !part.getName().equals(op.getOutput().getMessage().getName())) {
                                typesArray.add(part.getName());
                            }
                        }
                    }
                }
            }
        }
        return typesArray;
    }

}
