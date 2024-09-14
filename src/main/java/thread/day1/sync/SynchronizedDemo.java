package thread.day1.sync;

import java.util.concurrent.TimeUnit;

/**
 * case1：无synchronized修饰的对象，乱序输出
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        NoneSyncDemo noneSyncDemo = new NoneSyncDemo();
        new Thread(noneSyncDemo).start();
        new Thread(noneSyncDemo).start();
    }
}

class NoneSyncDemo implements Runnable {
    @Override
    public void run() {
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