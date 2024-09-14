package thread.day1;

import lombok.SneakyThrows;

/**
 * suspend和resume演示
 */
public class SuspendAndResumeDemo {
    public static Object u = new Object();

    static class ObjectThread extends Thread {
        public ObjectThread(String threadName) {
            super(threadName);
        }
        @Override
        public void run() {
            synchronized(u) {
                System.out.println(getName() + " is running!");
                Thread.currentThread().suspend(); /** 线程挂起，但【不释放任何锁资源】 */
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        ObjectThread thread1 = new ObjectThread("T1");
        ObjectThread thread2 = new ObjectThread("T2");
        thread1.start();
        Thread.sleep(100); /** 主线程睡眠100毫秒，防止在T1线程中，resume()先于suspend()被执行 */
        thread2.start();
        thread2.resume(); /** 由于T2还在阻塞中，所以此处调用T2的resume()，会导致resume()方法先于suspend()被执行 */
        thread1.resume(); /** 解除thread1的线程挂起 */
        thread1.join();
        thread2.join();
        System.out.println("SuspendAndResumeDemo is end!");
    }
}


