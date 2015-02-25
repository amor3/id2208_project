package work;

import arq.query;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author AMore
 */
public class WordnetMatcher {

    private static final String serviceURLSearcher = "http://www.abbreviations.com/services/v1/defs.aspx?tokenid=tk3847&word=";

    private Map<String, String> cache = new HashMap<>();
    
    public WordnetMatcher() {
    }

    public boolean matchWord(String input1, String input2) {
        
        if (cache.containsKey(input1)) {
            String cached = cache.get(input1);
            return cached.contains(input2);
        } else {

            String query = serviceURLSearcher + input1;
            String result = "";

            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;
                db = dbf.newDocumentBuilder();
                Document doc = db.parse(new URL(query).openStream());

                NodeList nodeList = doc.getElementsByTagName("term");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    String test = nodeList.item(i).getTextContent();
                    result += test;
                }

                result = result.toLowerCase().replace(input1, "");

                cache.put(input1, result);
                return result.contains(input2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String... args) {
        WordnetMatcher test = new WordnetMatcher();
        System.out.println(test.matchWord("area", "region"));
    }

}
