package thread.day1.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
    public static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    @SneakyThrows
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) new AddThread().start(); // 开启10个线程
        Thread.sleep(1000);
        System.out.println("atomicIntegerArray = " + atomicIntegerArray);
    }

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++)
                atomicIntegerArray.getAndIncrement(i % atomicIntegerArray.length()); // 将指定下标处的元素+1
        }
    }
}
