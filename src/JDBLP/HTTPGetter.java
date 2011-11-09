/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author trungnt
 */
public class HTTPGetter {
    private URL getter;
    public HTTPGetter() throws Exception {
        getter = new URL("http://dblp.uni-trier.de/search/author?xauthor=Nguyen+Thanh+Thuy");
        URLConnection con = getter.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ( (line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
    }
}
