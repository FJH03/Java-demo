package thread.day3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * 通过RecursiveAction的子类，实现无返回值的计算
 */
public class ForkJoinPoolDemo1 {
    public static void main(String[] args) throws Throwable {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> forkJoinTask = forkJoinPool.submit(new ActionTask(1, 2329));
        forkJoinTask.get();
        // TimeUnit.SECONDS.sleep(1); // 由于默认是Daemon线程，所以等待子线程执行完毕，否则主线程关闭，子线程也就结束了
        forkJoinPool.shutdown();
    }
}

/** 创建递归行为 */
class ActionTask extends RecursiveAction { // 适用于执行无返回值的业务逻辑
    private static final int THRESHOLD = 1000;
    private int start, end;

    public ActionTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int sum = 0;
        if (end - start < THRESHOLD) { // 满足一个批次数量
            for (long i = start; i <= end; i++) sum++;
            System.out.println(Thread.currentThread().getName() + " is done! sum=" + sum);
        } else { // 超出一个批次数量，需要拆包计算
            ActionTask actionTask;
            for (int i = 1, endNum = 0, startNum; i <= (end - start) / THRESHOLD + 1; i++) {
                startNum = (endNum == 0) ? start : endNum + 1;
                endNum = (startNum + THRESHOLD - 1) > end ? end : (startNum + THRESHOLD - 1); // 如果越界，则以end为边界
                actionTask = new ActionTask(startNum, endNum);
                actionTask.fork(); // 分支计算
            }
        }
    }
}
