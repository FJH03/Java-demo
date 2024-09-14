package thread.day2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * @author: muse
 **/
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock(); // 获得重入锁

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // 获得读写锁
    private static Lock readLock = readWriteLock.readLock(); // 获得读锁
    private static Lock writeLock = readWriteLock.writeLock(); // 获得写锁

    public static void main(String[] args) {
        boolean openRWLock = true;

        /** 开启3个写操作线程 */
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                if (openRWLock)
                    execute(writeLock, "开始执行【写】操作");
                else
                    execute(lock, "开始执行【写】操作");
            }).start();
        }

        /** 开启3个读操作线程 */
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                if (openRWLock)
                    execute(readLock, "开始执行【读】操作");
                else
                    execute(lock, "开始执行【读】操作");
            }).start();
        }
    }

    public static void execute(Lock lock, String msg) {
        try {
            lock.lock();
            System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + "，" + msg);
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
