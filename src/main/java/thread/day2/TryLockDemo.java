package thread.day2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock()的Demo演示
 */
public class TryLockDemo {
    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task).start();
        new Thread(task).start();
    }
}

@Slf4j
class Task implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            // if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
            if (lock.tryLock()) {
                System.out.println(System.currentTimeMillis() + " " + threadName + " lock!");
                Thread.sleep(100);
            } else {
                System.out.println(System.currentTimeMillis() + " " + threadName + " lock fail!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                System.out.println(System.currentTimeMillis() + " " + threadName + " unlock!");
                lock.unlock();
            }
        }
    }
}