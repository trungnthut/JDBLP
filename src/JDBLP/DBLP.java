/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author trungnt
 */
public class DBLP {

    private static List<AuthorInfo> cAuthorList = new ArrayList<AuthorInfo>();
    private static AuthorHandler cAuthorHandler = new AuthorHandler();
    private static SAXParser cSAXParser;

    static {
        cAuthorHandler.setList(cAuthorList);
        try {
            cSAXParser = SAXParserFactory.newInstance().newSAXParser();

            cSAXParser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation",
                    false);
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        }
    }

    public static List<AuthorInfo> queryAuthors(String name) throws Exception {
        InputStream result = DBLPGetter.queryAuthor(name);
        cSAXParser.parse(result, cAuthorHandler);
        return cAuthorList;
    }
}
