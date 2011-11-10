/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBLP;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author trungnt
 */
public class DBLPObject<T> {
    private static Map<String, DBLPObject> sObjectMap = new HashMap<String, DBLPObject>();
    
    public String key() {
        return "";
    }
    
    public static DBLPObject find(String key) {
        return sObjectMap.get(key);
    }
    
    public static boolean put(String key, DBLPObject  obj) {
        if (find(key) != null) {
            sObjectMap.put(key, obj);
            return true;
        }
        return false;
    }
}
