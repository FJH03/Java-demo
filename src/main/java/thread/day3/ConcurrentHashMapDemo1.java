package thread.day3;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @author: muse
 **/
public class ConcurrentHashMapDemo1 {
    public static void main(String[] args) throws Throwable {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        /** 下标0，横向插入8个node形成链表 */
        concurrentHashMap.put(0, "a0");     // 00000000
        concurrentHashMap.put(16, "a16");   // 00010000
        concurrentHashMap.put(32, "a32");   // 00100000
        concurrentHashMap.put(48, "a48");   // 00110000
        concurrentHashMap.put(64, "a64");   // 01000000
        concurrentHashMap.put(80, "a80");   // 01010000
        concurrentHashMap.put(96, "a96");   // 01100000
        concurrentHashMap.put(112, "a112"); // 01110000

        /** 下标1，横向插入4个node形成链表 */
        concurrentHashMap.put(1, "a1");     // 00000001
        concurrentHashMap.put(17, "a17");   // 00010001
        concurrentHashMap.put(33, "a33");   // 00100001
        concurrentHashMap.put(49, "a49");   // 00110001
    }

}
