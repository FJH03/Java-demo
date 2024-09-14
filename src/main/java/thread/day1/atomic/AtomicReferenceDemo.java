package thread.day1.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无锁的对象引用AtomicReference
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference(0);

    @SneakyThrows
    public static void main(String[] args) {
        for (int i=0; i< 100; i++) {
            new Thread(() -> {
                for (int j=0; j<1000;j++) {
                    while (true) {
                        Integer expectV = atomicReference.get();
                        if (atomicReference.compareAndSet(expectV, expectV+1)) break;
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("atomicInteger = " + atomicReference);
    }
}
