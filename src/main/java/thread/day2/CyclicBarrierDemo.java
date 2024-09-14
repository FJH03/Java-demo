package thread.day2;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试CyclicBarrier
 * <p>
 * 同学们去春游
 * 首先：同学们都先上公司门口的大巴。人齐了之后，巴士出发。
 * 其次：所有巴士都到达景点后，大家集合，开始春游。
 *
 * @author muse
 */
public class CyclicBarrierDemo {
    private static final int NUMS = 3; // 3个线程为1组
    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMS, new TeacherTask());
    public static void main(String[] args) {
        for (int i = 0; i < NUMS; i++)
            new Thread(new Student(i, cyclicBarrier)).start();
    }
}

class Student implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private volatile Integer studenNo;
    public Student(Integer studenNo, CyclicBarrier cyclicBarrier) {
        this.studenNo = studenNo;
        this.cyclicBarrier = cyclicBarrier;
    }

    @SneakyThrows
    @Override
    public void run() {
        // if (studenNo == 1) Thread.currentThread().interrupt(); // 针对学号为1的线程执行interrupt操作
        System.out.println("【学生" + studenNo + "】：\"老师，我已经上巴士车了！\"");
        cyclicBarrier.await();
        System.out.println("【学生" + studenNo + "】：\"老师，我所在的巴士车已经到达目的地！\"");
        cyclicBarrier.await();
    }
}

/** barrierAction：每当计数器一次计数完成后——CyclicBarrier.await()时，系统会执行的动作 */
class TeacherTask implements Runnable {
    private static int step = 1;
    @Override
    public void run() {
        if (step == 1)
            System.out.println("【王老师】：\"同学们都已经上大巴车了，咱们出发！\"");
        else if (step == 2)
            System.out.println("【王老师】：\"所有大巴车都到了，同学们开始春游！\"");
        step++;
    }
}