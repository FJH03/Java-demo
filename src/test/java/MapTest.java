/*
 */

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapTest {

    @Test
    public void testResize() {
        HashMap hashMap = new HashMap();
        /** 下标0和1，插入Node */
        hashMap.put(0, "a0");     // 00000000
        hashMap.put(1, "a1");     // 00000001

        /** 下标0，横向插入多个node形成链表 */
        hashMap.put(16, "a16");   // 00010000
        hashMap.put(32, "a32");   // 00100000
        hashMap.put(48, "a48");   // 00110000
        hashMap.put(64, "a64");   // 01000000
        hashMap.put(80, "a80");   // 01010000
        hashMap.put(96, "a96");   // 01100000
        hashMap.put(112, "a112"); // 01110000
        hashMap.put(128, "a128"); // 10000000
        // hashMap.put(144, "a144"); // 10010000
    }
}
