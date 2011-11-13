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
public class AuthorPublications {
    private Author mAuthor;
    private List<Publication> mPublications;
    
    public AuthorPublications(Author author) {
        mAuthor = author;
        mPublications = new ArrayList<Publication>();
    }
    
    public Author getAuthor() {
        return mAuthor;
    }
    
    public List<Publication> getPublications() {
        return mPublications;
    }
    
    public void addPublication(Publication p) {
        mPublications.add(p);
    }
}
