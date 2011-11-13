/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdblpdesktop;

import JDBLP.Author;
import JDBLP.AuthorInfo;
import JDBLP.AuthorPublications;
import JDBLP.DBLP;
import JDBLP.PersonPublications;
import JDBLP.Publication;
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
        List<Author> hmmList = DBLP.queryAuthors("Nguyen+Thanh+Thuy");

        for (Author a : hmmList) {
            System.out.printf("Got author %s - %s\n", a.getName(), a.getUrlpt());
        }

//        AuthorInfo author = new AuthorInfo("chahcahca", "lol");
//        System.out.printf("oh lala %s - %s\n", author.key(), author.getUrlpt());
        AuthorPublications ap = DBLP.queryAuthorPublications(hmmList.get(0));
        System.out.printf("Author %s - %s\n", ap.getAuthor().getName(), ap.getAuthor().getUrlpt());
        for (Publication p: ap.getPublications()) {
            System.out.printf("- Publication %s - %s\n", p.getKey(), p.getTitle());
        }

//        List<PersonPublications> ppList = PersonPublications.query(hmmList.get(0).getUrlpt());
//
//        for (PersonPublications p : ppList) {
//            System.out.printf("Got person %s with publication keys\n", p.getName());
//            for (String key : p.getPublications()) {
//                System.out.printf("\t %s\n", key);
//            }
//        }

//        Publication pub = Publication.query(ppList.get(0).getPublications().get(1));
//        if (pub != null) {
//            System.out.printf("Got publication at key %s\n", pub.getKey());
//            System.out.printf(" - Type: %s\n", Publication.typeName(pub.getType()));
//            System.out.printf(" - Title: %s\n", pub.getTitle());
//            System.out.print(" - mdate:");
//            System.out.println(pub.getMDate());
//            List<String> authors = pub.getAuthors();
//            for (String a: authors) {
//                System.out.printf(" - Author: %s\n", a);
//            }
//        }
    }
}
