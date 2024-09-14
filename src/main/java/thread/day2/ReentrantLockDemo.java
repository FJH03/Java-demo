package thread.day2;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁demo
 */
public class ReentrantLockDemo {
    public static int num;

    @SneakyThrows
    public static void main(String[] args) {
        CountTask task = new CountTask();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("num = " + num);
    }

    static class CountTask implements Runnable {
        public ReentrantLock lock = new ReentrantLock();
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start!");
            for (int i = 0; i < 10000000; i++) {
                lock.lock();
                try {
                    num++;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println(Thread.currentThread().getName() + " end!");
        }
    }
}
