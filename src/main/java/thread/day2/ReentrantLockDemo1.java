package thread.day2;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个线程多次获得锁和多次解锁
 */
public class ReentrantLockDemo1 {
    public static void main(String[] args) {
        new Thread(new Task()).start();
    }

    static class Task implements Runnable {
        public ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is lock!");
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is lock!");
            try {
                System.out.println(Thread.currentThread().getName() + " is coming!");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock!");
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock!");
                // lock.unlock();
                // System.out.println(Thread.currentThread().getName() + " is unlock!");
            }
        }
    }
}
