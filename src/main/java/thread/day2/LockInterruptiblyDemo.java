package thread.day2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试中断锁&非中断锁
 *
 * @author muse
 */
public class LockInterruptiblyDemo {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws Throwable {
        /** 可中断锁 */
        Thread t1 = new Thread(new ReentrantLockThread(lock1, lock2), "T1");
        Thread t2 = new Thread(new ReentrantLockThread(lock2, lock1), "T2");
        t1.start();
        t2.start();
        System.out.println("主线程开始沉睡第1秒！"); //主线程睡眠3秒，避免线程t1直接响应run方法中的睡眠中断
        TimeUnit.SECONDS.sleep(1);
        System.out.println("主线程线程，在"+ t1.getName() +"上开始执行interrupt()");
        t1.interrupt();
    }

    /** ReentrantLock的lockInterruptibly实现死锁 */
    static class ReentrantLockThread implements Runnable {
        private ReentrantLock lock1, lock2;

        public ReentrantLockThread(ReentrantLock lock1, ReentrantLock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }
        @Override
        public void run() {
            try {
                lock1.lockInterruptibly(); // 获得lock1的可中断锁
                System.out.println(Thread.currentThread().getName() + "，加锁成功1-2！");
                TimeUnit.MILLISECONDS.sleep(100); // 等待lock1和lock2分别被两个线程获取。产生死锁现象
                lock2.lockInterruptibly(); // 获得lock2的可中断锁
                System.out.println(Thread.currentThread().getName() + "，加锁成功2-2！");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "，发生异常！");
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName() + "，lock1解锁成功！");
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName() + "，lock2解锁成功！");
                }
            }
        }
    }

    /**
     * Synchronized实现死锁
     */
    static class SynchronizedThread implements Runnable {
        private Object lock1, lock2;

        public SynchronizedThread(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                synchronized(lock1) { // 获得lock1的不可中断锁
                    TimeUnit.MILLISECONDS.sleep(100); // 等待lock1和lock2分别被两个线程获取。产生死锁现象
                    synchronized(lock2) {} // 获得lock2的不可中断锁
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程" + Thread.currentThread().getName() + "正常结束");
            }
        }
    }
}
