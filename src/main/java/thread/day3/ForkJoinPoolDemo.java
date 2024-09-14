package thread.day3;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 通过RecursiveTask的子类，实现带返回值的计算
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) throws Throwable {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new ComputeTask(1, 2224));
        System.out.println("sum = " + forkJoinTask.get());
        forkJoinPool.shutdown();
    }
}

/** 创建递归任务 */
class ComputeTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000; // 1000为一个批次
    private int start, end;
    public ComputeTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start < THRESHOLD)
            for (long i = start; i <= end; i++) sum++; // 满足一个批次数量
        else { // 超出一个批次数量，需要拆包计算
            List<ComputeTask> computeTasks = Lists.newArrayList();
            for (int i = 1, endNum=0, startNum; i <= (end - start) / THRESHOLD + 1; i++) {
                startNum = (endNum == 0) ? start : endNum + 1;
                endNum = (startNum + THRESHOLD - 1) > end ? end : (startNum + THRESHOLD - 1); // 如果越界，则以end为边界
                ComputeTask computeTask = new ComputeTask(startNum, endNum); // 开启分支计算任务
                computeTasks.add(computeTask);
                computeTask.fork(); // 分支计算
            }
            for (ComputeTask task : computeTasks) {
                System.out.println(task.toString() + ": " + task.join());
                sum += task.join(); // 合并结果
            }
        }
        return sum;
    }
}