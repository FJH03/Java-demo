package thread.day3;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture的特性1：执行通知
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Throwable {
        CompletableFuture<Integer> future = new CompletableFuture();
        new Thread(()->{
            try {
                System.out.println("[子线程]执行future.get()尝试获得被通知的值");
                int result = future.get(); // 步骤2：获得通知
                System.out.println("[子线程]result = " + result);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("[主线程]睡眠1秒钟结束，并执行通知。future.complete(60)");
        future.complete(60); // 步骤1：执行通知
    }
}
