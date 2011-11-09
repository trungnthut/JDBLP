/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

/**
 *
 * @author trungnt
 */
public class AuthorInfo extends DBLPObject {
    private String mName;
    private String mUrlpt;
    
    public AuthorInfo(String name, String urlpt) {
        mName = name;
        mUrlpt = urlpt;
    }
    
    public AuthorInfo() {
        mName = "";
        mUrlpt = "";
    }
    
    public String getName() {
        return mName;
    }
    
    public void setName(String name) {
        if (!mName.equals(name)) {
            mName = name;
        }
    }
    
    public String getUrlpt() {
        return mUrlpt;
    }
    
    public void setUrlpt(String urlpt) {
        if (!mUrlpt.equals(urlpt)) {
            mUrlpt = urlpt;
        }
    }
    
    @Override
    public String key() {
        return getName();
    }
}
