package thread.day3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture的特性2：执行异步任务
 */
public class CompletableFutureDemo1 {
    public static void main(String[] args) throws Throwable {
        simple();
        System.out.println("----------------------------------------");
        thenCombine();
    }

    public static void simple() throws Throwable {
        System.out.println("[主线程]开始执行CompletableFuture异步任务");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> { // 开启异步任务
            System.out.println("[子线程]开始执行任务");
            try {
                TimeUnit.SECONDS.sleep(2); // 模拟业务处理耗时
                System.out.println("[子线程]睡眠2秒钟！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[子线程]任务执行完毕");
            return 1;
        });

        // 等待异步任务执行完毕，采用通过future.get()获得值
        System.out.println("[主线程]执行future.get()=" + future.get());
    }

    public static void thenCombine() throws Throwable {
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> {
            System.out.println("processing cfA...");
            return "Hello";
        });
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> {
            System.out.println("processing cfB...");
            return "World";
        });
        CompletableFuture<String> cfC = CompletableFuture.supplyAsync(() -> {
            System.out.println("processing cfC...");
            return "I'm Muse!";
        });
        CompletableFuture all = cfA.thenCombine(cfB, (resultA, resultB) -> resultA + "! " + resultB).
                thenCombine(cfC, (resultAB, resultC) -> resultAB + " " + resultC);
        System.out.println(all.get());
    }
}
