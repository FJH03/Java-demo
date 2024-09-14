package thread.day3;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        /** 第一步：创建ThreadPoolExecutor实例 */
        int corePoolSize = 2;
        int maximumPoolSize = 10;
        long keepAliveTime = 50;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);

        /** 第二步：调用execute方法执行任务 */
        for (int i =0; i< 10; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName() + ":" + atomicInteger.getAndIncrement()));
        }

        // executor.shutdown();

        // threadPoolTest(Executors.newCachedThreadPool());
        // threadPoolTest(Executors.newFixedThreadPool(3));
        // threadPoolTest(Executors.newSingleThreadExecutor());
        // threadPoolTest(executorService);

//      ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
//      for (int i = 1; i< 6; i++) {
//          executorService.scheduleAtFixedRate(() -> {
//              System.out.println(System.currentTimeMillis()/1000 + " ThreadName=" + Thread.currentThread().getName() +
//                      " num " + "schedule print!");
//          }, 1000, 2000, TimeUnit.MILLISECONDS);
//      }
    }

    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     */
    private static void threadPoolTest(ExecutorService threadPool) {
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    try {
                        System.out.println("Thread=" + Thread.currentThread().getName());
                        Thread.sleep(1000); // 去掉睡眠1秒，newCachedThreadPool则会出现线程复用的现象
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}
