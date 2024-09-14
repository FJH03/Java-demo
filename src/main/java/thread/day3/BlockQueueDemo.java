package thread.day3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockQueueDemo {
    public static void main(String[] args) throws Throwable {
        BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(10);
        System.out.println(System.currentTimeMillis() + "，begin poll element!");
        bq.poll(3, TimeUnit.SECONDS); // 如果bq中没有元素，则等待3秒
        System.out.println(System.currentTimeMillis() + "，3 seconds!");
        bq.poll(0, TimeUnit.SECONDS); // 如果bq中没有元素，直接返回
        System.out.println(System.currentTimeMillis() + "，0 seconds!");
    }
}
