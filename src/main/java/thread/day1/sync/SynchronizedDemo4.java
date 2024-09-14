package thread.day1.sync;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * [case5：synchronized修饰静态方法]
 * 修饰一个静态的方法，类锁，其作用的对象是这个类的所有对象；
 */
public class SynchronizedDemo4 {
    @SneakyThrows
    public static void main(String[] args) {
        // 加锁有效
        SyncStaticMethodDemo syncStaticMethodDemo = new SyncStaticMethodDemo();
        new Thread(syncStaticMethodDemo).start();
        new Thread(syncStaticMethodDemo).start();
        Thread.sleep(1000);
        System.out.println("----------------------------------");

        // 加锁有效
        new Thread(new SyncStaticMethodDemo()).start();
        new Thread(new SyncStaticMethodDemo()).start();
    }
}

class SyncStaticMethodDemo implements Runnable {
    @Override
    public void run() {
        method();
    }

    public synchronized static void method() {
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