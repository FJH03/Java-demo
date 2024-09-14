package thread.day1;

import java.util.concurrent.TimeUnit;

/**
 * volatile演示
 */
public class VolatileDemo {

    public static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
                    break;
                }
            }
        }, "T1").start();

        TimeUnit.MILLISECONDS.sleep(100); // 保证T1比T2先启动

        new Thread(()->{
            flag = true;
            System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
        }, "T2").start();
    }
}
