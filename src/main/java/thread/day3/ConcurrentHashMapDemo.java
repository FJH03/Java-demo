package thread.day3;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @author: muse
 **/
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int i = 0; i < 100; i++) {
            new Thread(()-> {
                String key;
                for (int n=0; n < 100; n++) {
                    key =  n + Thread.currentThread().getName();
                    hashMap.put(key, n);
                    concurrentHashMap.put(key, n);
                }
            }).start();
        }
        System.out.println(String.format("hashMap.size()=%s", hashMap.size()));
        System.out.println(String.format("concurrentHashMap.size()=%s", concurrentHashMap.size()));
    }
}
