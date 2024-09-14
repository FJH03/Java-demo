package thread.day2;

import java.util.concurrent.Semaphore;

/**
 * 测试Semaphore信号量
 * <p>
 * 广义上说，Semapore信号量是对锁的一种扩展；因为无论是内部锁synchronized，还是重入锁ReentrantLock，一次都只允许一个线程访问某一资源，而
 * 信号量却可以指定多个线程同时访问某一个资源。
 * <p>
 * [构造函数]
 *  public Semaphore(int permits) // permits：准入数
 *  public Semaphore(int permits, boolean fair) // permits：准入数，fair：是否公平获得锁
 * <p>
 * [重要方法]
 *  public void acquire();                  尝试获得一个准入的许可。若无法获得，则线程会等待，直到有线程释放一个许可或者当前线程被中断。
 *  public void acquireUninterruptibly();   具有acquire一样的功能，但是不响应中断。
 *  public void tryAcquire();               尝试获得一个许可，如果成功就返回true，失败则返回false。
 *  public void tryAcquire(long timeout, TimeUnit unit); 在指定时间内，尝试获得一个许可，如果成功就返回true，失败则返回false。
 *  public void release();                  资源访问结束后，释放一个许可。
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        SemaphoreTask semaphoreTask = new SemaphoreTask();
        for (int i = 0; i < 10; i++)
            new Thread(semaphoreTask).start();
    }
}

class SemaphoreTask implements Runnable {
    Semaphore semaphore = new Semaphore(4); // 允许4个线程同时访问某个资源

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();
            semaphore.acquire(); // 申请许可
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis() / 1000 + ", " + threadName + ", 执行完毕！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // 释放许可
        }
    }
}