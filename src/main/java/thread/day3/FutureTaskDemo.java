package thread.day3;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class FutureTaskDemo {
    @SneakyThrows
    public static void main(String[] args) {
        // 步骤1：构造FutureTask
        FutureTask futureTask = new FutureTask(new TaskCallable());

        // 步骤2：把FutureTask放入线程池中执行
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.submit(futureTask);
        System.out.println("[主线程]开启子线程");

        // 步骤3：此时如果需要子线程的计算结果，调用get即可(如果子线程未执行完毕，则此处阻塞，直到子线程执行完毕)
        System.out.println("[主线程]获取futureTask的结果，i=" + futureTask.get());
        threadPool.shutdown();
    }

    public static class TaskCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int i = 0;
            while (true) {
                if (i > 2) break;
                TimeUnit.MILLISECONDS.sleep(1000); // 模拟业务逻辑耗时
                System.out.println("[子线程]" + Thread.currentThread().getName() + " i=" + i++);
            }
            System.out.println("[子线程]" + Thread.currentThread().getName() + " is done!");
            return i;
        }
    }
}

