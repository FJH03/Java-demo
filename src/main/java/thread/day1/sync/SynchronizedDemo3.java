package thread.day1.sync;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * [case4：synchronized修饰方法，对象锁]
 * 一个线程访问一个对象中的synchronized修饰的方法时，其他试图访问该对象的线程将被阻塞。
 */
public class SynchronizedDemo3 {
    @SneakyThrows
    public static void main(String[] args) {
        // 加锁有效
        SyncMethodDemo syncMethodDemo = new SyncMethodDemo();
        new Thread(syncMethodDemo).start();
        new Thread(syncMethodDemo).start();
        Thread.sleep(1000);
        System.out.println("----------------------------------");

        // 加锁无效
        new Thread(new SyncMethodDemo()).start();
        new Thread(new SyncMethodDemo()).start();
    }
}

class SyncMethodDemo implements Runnable {
    @Override
    public synchronized void run() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
