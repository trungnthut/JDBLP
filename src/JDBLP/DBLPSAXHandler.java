/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.util.List;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author trungnt
 */
public class DBLPSAXHandler extends DefaultHandler {
    private List<DBLPObject> mObjectList;
    
        /**
     * w/o setting the list, empty result will be returned
     * @param authorList 
     */
    public void setList(List<DBLPObject> objectList) {
        mObjectList = objectList;
    }
    
    public List<DBLPObject> getList() {
        return mObjectList;
    }
}
