/*
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 */
public class ListTest {

    @Test
    public void testArrayList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("a1");

        System.out.println(arrayList);
        arrayList.set(0,"a2");
        System.out.println(arrayList);

        arrayList.remove(-1);

        arrayList.get(0);
    }

    @Test
    public void testLinkedList() {
        LinkedList linkedList = new LinkedList();
        linkedList.add("a1");
        linkedList.add("a2");
        linkedList.remove("a1");
    }
}
