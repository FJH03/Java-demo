package thread.day1;

import lombok.SneakyThrows;

/**
 * join方法演示
 */
public class JoinDemo {
    public volatile static int i = 0;

    public static class JoinThread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 100000; i++);
            System.out.println("线程JoinThread执行完毕！");
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        JoinThread joinThread = new JoinThread();
        joinThread.start();
        joinThread.join();
        System.out.println("i = " + i);
    }
}
