package thread.day2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试CountDownLatch
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Throwable {
        CountDownLatch countDownLatch = new CountDownLatch(3); // 设置计数器为3
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++)
            es.submit(new CountDownLatchRunning(countDownLatch, i+1)); // 一共生产3个线程。
        countDownLatch.await(); // 主线程等待3个子线程执行完毕后，继续往下执行
        System.out.println("3个子任务全部执行完毕！主线程继续执行");
        es.shutdown();
    }
}

class CountDownLatchRunning implements Runnable {
    private int i; // 线程序号
    private CountDownLatch countDownLatch;

    public CountDownLatchRunning(CountDownLatch countDownLatch, int i) {
        this.i = i;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(i); // 通过睡眠来模拟业务行为耗时

            // 如果子任务发生异常，也会在finally中执行countDown。不会影响其他线程和CountDownLatch
            if (i == 1) {
                System.out.println(System.currentTimeMillis() / 1000 + ", " + threadName + ": 子任务发生异常！");
                throw new RuntimeException();
            }
            System.out.println(System.currentTimeMillis() / 1000 + ", " + threadName + ": 子任务执行完毕！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown(); // 子线程任务执行完毕，调用countDown方法
        }
    }
}
