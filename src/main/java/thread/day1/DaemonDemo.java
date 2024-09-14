package thread.day1;

import lombok.SneakyThrows;

/**
 * 守护线程
 */
public class DaemonDemo {
    @SneakyThrows
    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); /** 将线程设置为守护线程 */
        daemonThread.start();
        Thread.sleep(2000);
        System.out.println("主线程DaemonDemo执行完毕！");
    }
}

class DaemonThread extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            System.out.println("I am alive!");
            Thread.sleep(500);
        }
    }
}
