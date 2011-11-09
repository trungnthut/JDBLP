/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdblpdesktop;

import JDBLP.Author;
import JDBLP.AuthorInfo;
import JDBLP.DBLP;
import JDBLP.PersonPublications;
import java.util.List;


/**
 *
 * @author trungnt
 */
public class JDBLPDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//        HTTPGetter getter = new HTTPGetter();
//        List<Author> hmmList = Author.queryAuthors("Nguyen+Thanh+Thuy");
        List<AuthorInfo> hmmList = DBLP.queryAuthors("Nguyen+Thanh+Thuy");
        
        for (AuthorInfo a: hmmList) {
            System.out.printf("Got author %s - %s\n", a.getName(), a.getUrlpt());
        }
        
        AuthorInfo author = new AuthorInfo("chahcahca", "lol");
        System.out.printf("oh lala %s - %s\n", author.key(), author.getUrlpt());
        
        List<PersonPublications> ppList = PersonPublications.queryPersonPublications(hmmList.get(0).getUrlpt());
        
        for (PersonPublications p: ppList) {
            System.out.printf("Got person %s with publication keys\n", p.getName());
            for (String key: p.getPublications()) {
                System.out.printf("\t %s\n", key);
            }
        }
    }
}
