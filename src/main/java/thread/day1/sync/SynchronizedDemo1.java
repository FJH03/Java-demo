package thread.day1.sync;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * [case2：synchronized修饰代码块——对象]
 * 一个线程访问一个对象中的synchronized(this)同步代码块时，其他试图访问该对象的线程将被阻塞。
 */
public class SynchronizedDemo1 {
    @SneakyThrows
    public static void main(String[] args) {
        // 加锁有效
        SyncBlockDemo syncBlockDemo = new SyncBlockDemo();
        new Thread(syncBlockDemo).start();
        new Thread(syncBlockDemo).start();
        Thread.sleep(1000);

        System.out.println("----------------------------------");

        // 加锁无效
        new Thread(new SyncBlockDemo()).start();
        new Thread(new SyncBlockDemo()).start();
    }
}

class SyncBlockDemo implements Runnable {
    @Override
    public void run() {
        synchronized(this) {
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
}