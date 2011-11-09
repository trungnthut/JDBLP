/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.util.Date;
import java.util.List;

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
    private Type mType;
    private String mKey;
    private Date mModicationDate;
    private List<String> mAuthors;
    private String mTitle;
    private String mPages;
    private String mYear;
    private String mVolume;
    private String mJournal;
    private int mNumber;
    private String mUrl;
    private String mEE;
}
