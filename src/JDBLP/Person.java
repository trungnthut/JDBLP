/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

/**
 *
 * @author trungnt
 */
public class Person {
    private String mUrltp;
    private String mName;
    
    public Person(String urltp, String name) {
        mUrltp = urltp;
        mName = name;
    }
    
    public String getUrltp() {
        return mUrltp;
    }
    
    public String getName() {
        return mName;
    }
    
    public void setUrltp(String urltp) {
        if (!urltp.equals(mUrltp)) {
            mUrltp = urltp;
        }
    }
    
    public void setName(String name) {
        if (!name.equals(mName)) {
            mName = name;
        }
    }
}
