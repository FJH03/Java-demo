package thread.day1.sync;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * [case3：synchronized修饰代码块——类]
 * 修饰一个类，即：类锁，其作用的范围是synchronized后面括号括起来的部分，作用的对象是这个类的所有对象；
 */
public class SynchronizedDemo2 {
    @SneakyThrows
    public static void main(String[] args) {
        // 加锁有效
        SyncClassDemo syncClassDemo = new SyncClassDemo();
        new Thread(syncClassDemo).start();
        new Thread(syncClassDemo).start();
        Thread.sleep(1000);
        System.out.println("----------------------------------");

        // 加锁有效
        new Thread(new SyncClassDemo()).start();
        new Thread(new SyncClassDemo()).start();
    }
}

class SyncClassDemo implements Runnable {
    @Override
    public void run() {
        synchronized(SyncClassDemo.class) {
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