package thread.day1;

import lombok.SneakyThrows;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue演示
 */
public class SynchronousQueueDemo {

    @SneakyThrows
    public static void main(String[] args) {
        SynchronousQueue synchronousQueue = new SynchronousQueue();

        /** 测试1：只执行保存元素操作 */
        synchronousQueue.offer(2); // 放入元素 DATA

        /** 测试2：先执行阻塞获取元素操作，再执行保存元素操作 */
        new Thread(() -> {
            try {
                synchronousQueue.poll(100, TimeUnit.SECONDS); // 阻塞获取元素 REQUEST
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000); // 保证子线程先针对queue执行poll操作
        synchronousQueue.offer(2); // 放入元素  DATA
    }
}
