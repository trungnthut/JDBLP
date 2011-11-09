/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author trungnt
 */
public class AuthorHandler extends DefaultHandler {
    private boolean mParsing;
    private String mUrlpt;
    private String mName;
    private List<AuthorInfo> mAuthorList;

    @Override
    public void startElement(String namespaceURI, String localName,
            String rawName, Attributes attrs) throws SAXException {
        if (rawName.equals("authors") && mAuthorList != null) {
            mAuthorList.clear();
            return;
        }
        if (mParsing = rawName.equals("author")) {
            mName = "";
            mUrlpt = attrs.getValue("urlpt");
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName,
            String rawName) throws SAXException {
        if (rawName.equals("author") && mName.length() > 0 && mAuthorList != null) {
            mAuthorList.add(new AuthorInfo(mName, mUrlpt));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (mParsing) {
            mName += new String(ch, start, length);
        }
    }
    
    /**
     * w/o setting the list, empty result will be returned
     * @param authorList 
     */
    public void setList(List<AuthorInfo> authorList) {
        mAuthorList = authorList;
    }
    
    public List<AuthorInfo> getList() {
        return mAuthorList;
    }
}
