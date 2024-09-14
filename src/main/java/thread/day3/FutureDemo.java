package thread.day3;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo {
    @SneakyThrows
    public static void main(String[] args) {
        // 步骤1：构建线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

        // 步骤2：把Callable放入线程池中执行
        List<Future<String>> result = Lists.newArrayList();
        for (int i=0; i < 4; i++)
            result.add(fixedThreadPool.submit(new TaskCallable())); // 通过submit返回Future

        // 步骤3：此时如果需要子线程的计算结果，调用get即可(如果子线程未执行完毕，则此处阻塞，直到子线程执行完毕)
        System.out.println("[主线程]开始尝试获得所有Future结果！");
        for (Future<String> item : result)
            System.out.println("[主线程]获取futureTask的结果，result=" + item.get()); // 调用Future#get获得子线程返回的结果

        fixedThreadPool.shutdown();
    }

    public static class TaskCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            int sleepTime = new Random().nextInt(100);
            TimeUnit.MILLISECONDS.sleep(sleepTime); // 模拟业务逻辑耗时
            System.out.println("[子线程]" + Thread.currentThread().getName() + " sleepTime=" + sleepTime + "毫秒");
            return Thread.currentThread().getName();
        }
    }
}
