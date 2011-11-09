/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author trungnt
 */
public class Author {

    private static Map<String, Author> sAuthorMap = new HashMap<String, Author>();
    private String mName;
    private String mUrltp;
    private static SAXParser sSaxParser;
    private static AuthorHandler sXMLHandler;
    private static List<Author> sFoundAuthors = new ArrayList<Author>();

    static {
        try {
            sSaxParser = SAXParserFactory.newInstance().newSAXParser();
            sXMLHandler = new AuthorHandler();
            sSaxParser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation",
                    false);
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        }
    }

    public static Author searchAuthor(String name) {
        return sAuthorMap.get(name);
    }

    public static List<Author> queryAuthors(String name) throws Exception {
        sFoundAuthors.clear();
        InputStream result = DBLPGetter.queryAuthor(name);
        sSaxParser.parse(result, sXMLHandler);
        return sFoundAuthors;
    }
    
    public String getUrlpt() {
        return mUrltp;
    }
    
    public String getName() {
        return mName;
    }

    private Author(String name, String urlpt) {
        mName = name;
        this.mUrltp = urlpt;
        sAuthorMap.put(name, this);
    }

    private static Author createAuthor(String name, String urlpt) {
        // TODO: what is 2 authors have same name
        Author a = searchAuthor(name);
        if (a == null) {
            a = new Author(name, urlpt);
        }
        return a;
    }

    // XML Author Handler class
    private static class AuthorHandler extends DefaultHandler {

        private boolean mParsing;
        private String mUrlpt;
        private String mName;

        @Override
        public void startElement(String namespaceURI, String localName,
                String rawName, Attributes attrs) throws SAXException {
            if (mParsing = rawName.equals("author")) {
                mName = "";
                mUrlpt = attrs.getValue("urlpt");
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName,
                String rawName) throws SAXException {
            if (rawName.equals("author") && mName.length() > 0) {
                sFoundAuthors.add(createAuthor(mName, mUrlpt));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (mParsing) {
                mName += new String(ch, start, length);
            }
        }
    }
}
