package thread.day2;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类测试示例
 * 验证即使是先执行unpark，随后执行park操作，依然可以让park操作释放阻塞
 */
public class LockSupportDemo {
    public static void main(String[] args) throws Throwable {
        System.out.println(System.currentTimeMillis() + " [主线程]开始执行");
        Thread thread = new Thread(new LockSupportDemoTask());
        thread.start();
        System.out.println(System.currentTimeMillis() + " [主线程]执行了LockSupport.unpark(thread) ，释放对子线程的阻塞");
        LockSupport.unpark(thread); /** unpark方法，将thread的一个许可从[不可用状态]变为［可用状态］*/
    }

    static class LockSupportDemoTask implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " [子线程]开始运行并且先sleep一秒");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(System.currentTimeMillis() + " [子线程]执行LockSupport.park()方法，阻塞本线程");
            LockSupport.park(); /** park方法，将thread的一个许可从[可用状态]变为［不可用状态］*/
            System.out.println(System.currentTimeMillis() + " [子线程]运行结束");
        }
    }
}
