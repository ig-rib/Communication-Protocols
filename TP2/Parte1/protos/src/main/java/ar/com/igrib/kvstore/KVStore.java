package ar.com.igrib.kvstore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KVStore {

    private final Map<String, String> dict;

    public KVStore () {
        HashMap<String, String> hashMap = new HashMap<>();
        dict = Collections.synchronizedMap(hashMap);
    }

    public int setKeyValue(String key, String value) {
        dict.put(key, value);
        return 1;
    }

    public int incrementKeyValue(String key) {
        dict.put(key, String.valueOf(Integer.parseInt(dict.get(key))+1));
        return 1;
    }
   
    public int decrementKeyValue(String key) {
        dict.put(key, String.valueOf(Integer.parseInt(dict.get(key))-1));
        return 1;
    }

    public String getKeyValue(String key) {
        return dict.get(key);
    }
}