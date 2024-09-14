package thread.day3;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 演示ConcurrentLinkedQueue基本操作，用于源码解析debug
 */
public class ConcurrentLinkedQueueDemo1 {

    public static void main(String[] args) {
        // 演示1：添加元素
        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue();
        linkedQueue.add("muse");
        linkedQueue.add("bob");

        // 演示2：移除元素
        linkedQueue.remove();
        linkedQueue.remove();

        // 演示3：哨兵
        linkedQueue = new ConcurrentLinkedQueue();
        linkedQueue.add("muse");
        linkedQueue.remove();
        linkedQueue.add("bob");
    }

}
