/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class Publication {

    public enum Type {

        ARTICLE,
        INPROCEEDINGS,
        PROCEEDINGS,
        BOOK,
        INCOLLECTION,
        PHDTHESIS,
        MASTERSTHESIS,
        WWW
    }
    private static Map<String, Publication> sPublicationMap = new HashMap<String, Publication>();
    private static SAXParser sSaxParser;
    private static PublicationHandler sXMLHandler;
    private static Publication sFoundPublication;

    static {
        try {
            sSaxParser = SAXParserFactory.newInstance().newSAXParser();
            sXMLHandler = new PublicationHandler();
            sSaxParser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation",
                    false);
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        }
    }
    private Type mType;
    private String mKey;
    private Date mModicationDate;
    private List<String> mAuthors;
    private String mTitle;
//    private String mPages;
//    private String mYear;
//    private String mVolume;
//    private String mJournal;
//    private int mNumber;
//    private String mUrl;
//    private String mEE;

    public static Publication search(String key) {
        return sPublicationMap.get(key);
    }
    
    public static Publication query(String key) throws Exception {
        sFoundPublication = null;
        InputStream result = DBLPGetter.queryRecord(key);
        sSaxParser.parse(result, sXMLHandler);
        return sFoundPublication;
    }
    
    public Type getType() {
        return mType;
    }
    
    public void setType(Type type) {
        mType = type;
    }
    
    public String getKey() {
        return mKey;
    }
    
    public void setKey(String key) {
        mKey = key;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public void setTitle(String title) {
        mTitle = title;
    }
    
    public List<String> getAuthors() {
        return mAuthors;
    }
    
    public void addAuthor(String author) {
        if (!mAuthors.contains(author)) {
            mAuthors.add(author);
        }
    }
    
    /**
     * get modification date. Note, only day, month and year are valid!
     * @return Date
     */
    public Date getMDate() {
        return mModicationDate;
    }
    
    public static String typeName(Type type) {
        switch (type) {
            case ARTICLE:
                return "article";
            case INPROCEEDINGS:
                return "inproceedings";
            case PROCEEDINGS:
                return "proceedings";
            case BOOK:
                return "book";
            case INCOLLECTION:
                return "incollection";
            case PHDTHESIS:
                return "phdthesis";
            case MASTERSTHESIS:
                return "masterthesis";
            case WWW:
                return "www";
        }
        return "unknown";
    }

    private Publication(Type type, String key, Date mdate) {
        mType = type;
        mKey = key;
        mModicationDate = mdate;
        mAuthors = new ArrayList<String>();
        mTitle = "";
        sPublicationMap.put(key, this);
    }

    private static Publication create(Type type, String key, Date date) {
        Publication p = search(key);
        if (p != null) {
            return p;
        }

        return (new Publication(type, key, date));
    }

    private static class PublicationHandler extends DefaultHandler {
        private boolean mParsing;
        private Publication mPublication;
        private Type mType;
        private boolean mNeedToGetKey;
        private String mKey;
        private Date mDate;
        private static DateFormat mFormater = new SimpleDateFormat("yyyy-MM-dd");
        private boolean inAuthor;
        private boolean inTitle;
        private String mAuthor;
        private String mTitle;

        @Override
        public void startElement(String namespaceURI, String localName,
                String rawName, Attributes attrs) throws SAXException {
            if (rawName.equals("dblp")) {
                mParsing = true;
                mPublication = null;
                return;
            }
            mNeedToGetKey = false;
            if (rawName.equals("article")) {
                mType = Type.ARTICLE;
                mNeedToGetKey = true;
            } else if (rawName.equals("inproceedings")) {
                mType = Type.INPROCEEDINGS;
                mNeedToGetKey = true;
            } else if (rawName.equals("proceedings")) {
                mType = Type.PROCEEDINGS;
                mNeedToGetKey = true;
            } else if (rawName.equals("book")) {
                mType = Type.BOOK;
                mNeedToGetKey = true;
            } else if (rawName.equals("incollection")) {
                mType = Type.INCOLLECTION;
                mNeedToGetKey = true;
            } else if (rawName.equals("phdthesis")) {
                mType = Type.PHDTHESIS;
                mNeedToGetKey = true;
            } else if (rawName.equals("mastersthesis")) {
                mType = Type.MASTERSTHESIS;
                mNeedToGetKey = true;
            } else if (rawName.equals("www")) {
                mType = Type.WWW;
                mNeedToGetKey = true;
            }
            
            if (mNeedToGetKey) {
                mKey = attrs.getValue("key");
                String mdate = attrs.getValue("mdate");
                try {
                    mDate = (Date)mFormater.parse(mdate);
                } catch (ParseException e) {
                    System.out.println("Error when parsing mdate !");
                }
                
                mPublication = create(mType, mKey, mDate);
                return;
            }
            
            inAuthor = false;
            inTitle = false;
            if (rawName.equals("author")) {
                mAuthor = "";
                inAuthor = true;
            } else if (rawName.equals("title")) {
                mTitle = "";
                inTitle = true;
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName,
                String rawName) throws SAXException {
            if (rawName.equals("dblp") && mPublication != null) {
                sFoundPublication = mPublication;
                mPublication = null;
                return;
            }
            if (rawName.equals("author") && mPublication != null && inAuthor && !mAuthor.isEmpty()) {
                mPublication.addAuthor(mAuthor);
                inAuthor = false;
                mAuthor = "";
                return;
            }
            
            if (rawName.equals("title") && mPublication != null && inTitle && !mTitle.isEmpty()) {
                mPublication.setTitle(mTitle);
                inTitle = false;
                mTitle = "";
                return;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (mParsing) {
                if (inAuthor) {
                    mAuthor += new String(ch, start, length);
                } else if (inTitle) {
                    mTitle += new String(ch, start, length);
                }
            }
        }
    }
}
