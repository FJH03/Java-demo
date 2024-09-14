package thread.day2;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁与非公平锁Demo
 */
public class FairLockDemo {
    public static void main(String[] args) {
        FairLockTask fairLockTask = new FairLockTask();
        new Thread(fairLockTask, "T1").start();
        new Thread(fairLockTask, "T2").start();
        new Thread(fairLockTask, "T3").start();
        new Thread(fairLockTask, "T4").start();
        new Thread(fairLockTask, "T5").start();
        new Thread(fairLockTask, "T6").start();
    }
}

class FairLockTask implements Runnable {
    ReentrantLock lock = new ReentrantLock(false); // 开启非公平锁

    @SneakyThrows
    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " lock!");
        } finally {
            lock.unlock();
        }
    }
}