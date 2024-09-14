package thread.day1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal演示
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws Throwable {
        ThreadLocal tl1 = new ThreadLocal();
        tl1.set("muse");
        tl1.set("bob");
        System.out.println("tl1.get() = " + tl1.get());

        ThreadLocal tl2 = new ThreadLocal();
        tl2.set("john");
        System.out.println("tl2.get() = " + tl2.get());

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i=0; i<10; i++)
            executor.execute(() -> tl1.set(Thread.currentThread().getName()));

        Thread.sleep(100);
        for (int i=0; i<10; i++)
            executor.execute(() -> System.out.println("args = " + tl1.get()));

        executor.shutdown();
    }
}
