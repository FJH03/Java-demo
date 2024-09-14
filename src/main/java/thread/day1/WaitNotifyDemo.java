package thread.day1;

/**
 * wait和notify演示
 */
public class WaitNotifyDemo {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new T1().start();
        new T2().start();
    }

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized(obj) {
                System.out.println(System.currentTimeMillis() + ":T1 start!");
                try {
                    /** Step1: 释放obj对象锁，进入obj对象的wait队列 */
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object!");
                    obj.wait();
                    // obj.wait(1000); // 如果1秒钟没有被notify，则直接放弃等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /** Step3: T2执行完毕，T1需要获得obj锁，才可以继续执行 */
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            // 由于Step1释放obj的锁，所以T2可以获得锁，然后运行
            synchronized(obj) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                /** Step2: 唤醒T1，但是由于T2没有执行完毕而释放obj的锁，所以T1等待中 */
                obj.notify();
                System.out.println(System.currentTimeMillis() + ":T2 notify end!");
                try {
                    Thread.sleep(2000);
                    System.out.println(System.currentTimeMillis() + ":T2 sleep 2000ms end!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
