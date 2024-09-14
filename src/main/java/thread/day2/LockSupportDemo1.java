package thread.day2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类测试示例2：
 * 除了有阻塞功能外，LockSupport.park()还能支持中断。但是它不会抛InterruptedException异常。它只会默默的返回，但是我们可以从
 * Thread.interrupted()等方法获得中断标记
 */
public class LockSupportDemo1 {
    public static void main(String[] args) throws Throwable {
        RunningDemo runningDemo = new RunningDemo();
        Thread thread1 = new Thread(runningDemo, "T1");
        Thread thread2 = new Thread(runningDemo, "T2");
        thread1.start();
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("[主线程]执行interrupt()打断子线程" + thread1.getName());
        thread1.interrupt();
        TimeUnit.MILLISECONDS.sleep(100);
        LockSupport.unpark(thread2); /** unpark方法，将thread1的一个许可变为［可用］状态*/
        System.out.println("[主线程]针对子线程" + thread2.getName() + "执行LockSupport.unpark() 完毕！");
    }

    static class RunningDemo implements Runnable {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("[子线程]" + threadName + "开始运行！执行LockSupport.park()方法，阻塞本线程!");
            /**
             * park()可以阻塞当前线程
             * 每一个线程有一个许可，该许可默认为[不可用]。
             * 如果该许可是[可用]状态，那么park()方法会立即返回，消费这个许可，将该许可消变更为[不可用]状态，流程代码可以继续执行。
             */
            LockSupport.park();

            /** 也可以使用Thread.currentThread().isInterrupted() */
            if (Thread.interrupted()) {
                System.out.println("[子线程]" + threadName + "被interrupt()方法打断，但并不会抛出异常。");
            }
            System.out.println("[子线程]" + threadName + "运行结束！");
        }
    }
}

