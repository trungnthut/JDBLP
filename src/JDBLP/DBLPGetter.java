/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author trungnt
 */
public class DBLPGetter {
    private static String sAuthorURLFormat = "http://dblp.uni-trier.de/search/author?xauthor=%s";
    private static String sPersonPublishFormat = "http://dblp.uni-trier.de/rec/pers/%s/xk";
    private static String sPersonCoAuthorsFormat = "http://dblp.uni-trier.de/rec/pers/%s/xc";
    private static String sRecordFormat = "http://dblp.uni-trier.de/rec/bibtex/%s.xml";
    
    private static URLConnection establishConnection(String queryFormat, String param) throws Exception {
        String getUrl = "".format(queryFormat, param);
        System.out.println(getUrl);
        URL getter = new URL(getUrl);
        URLConnection conn = getter.openConnection();
        return conn;
    }
    
    private static InputStream query(String queryFormat, String param) throws Exception {
        return establishConnection(queryFormat, param).getInputStream();
    }
    
    public static URLConnection establishAuthorQuery(String name) throws Exception {
        return establishConnection(sAuthorURLFormat, name);
    }
    
    public static URLConnection establishPersonPublishQuery(String urlpt) throws Exception {
        return establishConnection(sPersonPublishFormat, urlpt);
    }
    
    public static URLConnection establishPersonCoAuthorsQuery(String urlpt) throws Exception {
        return establishConnection(sPersonCoAuthorsFormat, urlpt);
    }
    
    public static URLConnection establishRecordQuery(String key) throws Exception {
        return establishConnection(sRecordFormat, key);
    }
    
    public static InputStream queryAuthor(String name) throws Exception {
        return query(sAuthorURLFormat, name);
    }
    
    public static InputStream queryPersonPublish(String urlpt) throws Exception {
        return query(sPersonPublishFormat, urlpt);
    }
    
    public static InputStream queryPersonCoAuthor(String urlpt) throws Exception {
        return query(sPersonCoAuthorsFormat, urlpt);
    }
    
    public static InputStream queryRecord(String key) throws Exception {
        return query(sRecordFormat, key);
    }
}
