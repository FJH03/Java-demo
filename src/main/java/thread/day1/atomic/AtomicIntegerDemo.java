package thread.day1.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无锁的线程安全整数AtomicInteger
 */
public class AtomicIntegerDemo {
    static AtomicInteger atomicInteger = new AtomicInteger();
    static Demo demo = new Demo();

    @SneakyThrows
    public static void main(String[] args) {
        for (int i=0; i< 100; i++) {
            new Thread(() -> {
                for (int j=0; j<1000;j++) {
                    atomicInteger.incrementAndGet();
                    demo.score++;
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("atomicInteger = " + atomicInteger);
        System.out.println("demo.score = " + demo.score);
    }
}

class Demo {
    volatile int score;
}
