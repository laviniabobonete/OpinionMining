package keyword;

import com.alchemyapi.api.AlchemyAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordsRetrieval {

    public static final String KEY_PATH = "D:\\School\\Disertatie\\git\\OpinionMining\\AlchemyAPI_Java-0.8\\testdir\\api_key.txt";

    /**
     * Gets list of keywords from text
     */
    public static List<Keyword> getKeywords(String text) {

        List<Keyword> keywords = new ArrayList<Keyword>();

        try {
            // Create an AlchemyAPI object.
            AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile(KEY_PATH);

            // Extract topic keywords for a text string.
            Document doc = alchemyObj.TextGetRankedKeywords(text);

            // Extract keywords from dom
            NodeList nList = doc.getElementsByTagName("keyword");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String keyText = eElement.getElementsByTagName("text").item(0).getTextContent();
                    float keyRelevance = Float.parseFloat(eElement.getElementsByTagName("relevance").item(0).getTextContent());
                    keywords.add(new Keyword(keyText, keyRelevance));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keywords;
    }

    /**
     * Creates a String from the xml document
     */
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
