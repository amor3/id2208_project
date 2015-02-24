package work;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author AMore
 */
public class WordnetMatcher {

    private static final String serviceURLSearcher = "http://www.abbreviations.com/services/v1/defs.aspx?tokenid=tk1765&word=";

    private WordnetMatcher() {
    }

    public static boolean matchWord(String input1, String input2) {
        String query = serviceURLSearcher + input1;
        String result = "";
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(query).openStream());
            
            NodeList nodeList = doc.getElementsByTagName("term");
            
            for(int i = 0; i < nodeList.getLength(); i++){
                String test = nodeList.item(i).getTextContent();
                result += test;
            }
        
            result = result.toLowerCase().replace(input1, "");
        
            return result.contains(input2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String... args) {
        WordnetMatcher test = new WordnetMatcher();
        System.out.println( test.matchWord("area", "region") );
    }

}
