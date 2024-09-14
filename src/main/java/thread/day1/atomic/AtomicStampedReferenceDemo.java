package thread.day1.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {
    public static AtomicStampedReference<Integer> reference = new AtomicStampedReference(1, 0);

    public static void main(String[] args) {
        new AThread().start(); // 开启A线程
        new BThread().start(); // 开启B线程
    }

    /** A线程 (将值加1) */
    public static class AThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            Integer ref = reference.getReference();
            Integer stamp = reference.getStamp();
            System.out.println("[AThread] Before Sleep: ref=" + ref + ", stamp=" + stamp);

            Thread.sleep(1000); // 睡眠1秒钟
            System.out.println("[AThread] After Sleep: ref=" + reference.getReference() + ", stamp=" + reference.getStamp());

            if (reference.compareAndSet(ref, ref + 1, stamp, stamp + 1))
                System.out.println("[AThread] Add one success!");
            else
                System.out.println("[AThread] Add one fail!");
        }
    }

    /** B线程 (先加2，再还原为原值) */
    public static class BThread extends Thread {
        private boolean flag = false;
        private Integer originRef = 0;

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Integer ref = reference.getReference();
                Integer stamp = reference.getStamp();
                if (flag) {
                    if (reference.compareAndSet(ref, originRef, stamp, stamp + 1)) { // step2：恢复原值
                        System.out.println("[BThread] ref=" + reference.getReference() + ",stamp=" + reference.getStamp());
                        break;
                    }
                } else {
                    if (reference.compareAndSet(ref, ref + 2, stamp, stamp + 1)) { // step1：在原值上加2
                        System.out.println("[BThread] ref=" + reference.getReference() + ",stamp=" + reference.getStamp());
                        originRef = ref;
                        flag = true;
                    }
                }
                Thread.sleep(100);
            }
        }
    }
}


