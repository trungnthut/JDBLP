/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author trungnt
 */
public class DBLP {
    public static List<Author> queryAuthors(String authorName) throws Exception {
        return Author.query(authorName);
    }
    
    public static PersonPublications queryPersonPublications(String urlpt) throws Exception {
        return PersonPublications.query(urlpt);
    }
    
    public static Publication queryPublication(String key) throws Exception {
        return Publication.query(key);
    }
    
    public static AuthorPublications queryAuthorPublications(Author a) throws Exception {
//        List<Author> authList = queryAuthors(authorName);
//        if (authList == null || authList.isEmpty()) {
//            return null;
//        }
        
        AuthorPublications ap = new AuthorPublications(a);
        
//        for (Author a: authList) {
            PersonPublications pp = queryPersonPublications(a.getUrlpt());
            if (pp != null || !pp.getPublications().isEmpty()) {
                for (String key: pp.getPublications()) {
                    Publication p = queryPublication(key);
                    if (p!= null) {
                        ap.addPublication(p);
                    }
                }

            } 
//        }
        
        return ap;
    }
}
