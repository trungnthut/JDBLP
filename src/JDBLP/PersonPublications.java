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
public class PersonPublications {

    private static Map<String, PersonPublications> sPersonPublicationsMap = new HashMap<String, PersonPublications>();
    private String mName; // well, author !
    private List<String> mPublications;
    private static SAXParser sSaxParser;
    private static PersonPublicationsHandler sXMLHandler;
    private static PersonPublications sFoundPersonPublications;

    static {
        try {
            sSaxParser = SAXParserFactory.newInstance().newSAXParser();
            sXMLHandler = new PersonPublicationsHandler();
            sSaxParser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation",
                    false);
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        }
    }

    public static PersonPublications search(String personName) {
        return sPersonPublicationsMap.get(personName);
    }
    
    public static PersonPublications query(String urlpt) throws Exception {
        InputStream result = DBLPGetter.queryPersonPublish(urlpt);
        sSaxParser.parse(result, sXMLHandler);
        return sFoundPersonPublications;
    }

    public List<String> getPublications() {
        return mPublications;
    }

    public String getName() {
        return mName;
    }

    public boolean addPublication(String key) {
        if (key.length() > 0) {
            mPublications.add(key);
            return true;
        }
        return false;
    }

    private PersonPublications(String personName) {
        mName = personName;
        mPublications = new ArrayList<String>();
        sPersonPublicationsMap.put(personName, this);
    }

    private static PersonPublications create(String personName) {
        if (personName.length() <= 0) {
            return null;
        }
        PersonPublications pp = search(personName);
        if (pp != null) {
            return search(personName);
        }
        pp = new PersonPublications(personName);
        return pp;
    }

    private static class PersonPublicationsHandler extends DefaultHandler {

        private boolean mParsing;
        private PersonPublications mPerson;
        private String mPublication;
        private boolean mInPublication;

        @Override
        public void startElement(String namespaceURI, String localName,
                String rawName, Attributes attrs) throws SAXException {
            if (rawName.equals("dblpperson")) {
                sFoundPersonPublications = null;
                mParsing = true;
                String name = attrs.getValue("name");
                mPerson = create(name);
            } else if (mParsing && rawName.equals("dblpkey") && mPerson != null) {
                mPublication = "";
                mInPublication = true;
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName,
                String rawName) throws SAXException {
            if (rawName.equals("dblpkey") && mInPublication) {
                if (mPublication.length() > 0) {
                    mPerson.addPublication(mPublication);
                }
                mInPublication = false;
            } else if (rawName.equals("dblpperson") && mPerson != null) {
                sFoundPersonPublications = mPerson;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (mInPublication) {
                mPublication += new String(ch, start, length);
            }
        }
    }
}
