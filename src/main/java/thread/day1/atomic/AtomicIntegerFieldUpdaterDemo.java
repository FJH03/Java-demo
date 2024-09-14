package thread.day1.atomic;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    public static AtomicInteger allScore = new AtomicInteger(0);

    @SneakyThrows
    public static void main(String[] args) {
        final Candidate candidate = new Candidate();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    scoreUpdater.incrementAndGet(candidate);
                    allScore.incrementAndGet();
                    candidate.scoreTemp++;
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("score = " + candidate.score);
        System.out.println("allScore = " + allScore);
        System.out.println("scoreTemp = " + candidate.scoreTemp);
    }
}

class Candidate {
    volatile int score;
    volatile int scoreTemp;
}
