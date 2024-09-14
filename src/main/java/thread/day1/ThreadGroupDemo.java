package thread.day1;

import lombok.SneakyThrows;

/**
 * 线程组演示
 */
public class ThreadGroupDemo {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Thread-Group");
        Thread t1 = new Thread(threadGroup, new Task(), "T1");
        Thread t2 = new Thread(threadGroup, new Task(), "T2");
        t1.start();
        t2.start();
        /** activeCount()方法用于获得活动线程的总数，由于线程是动态的，所以这个值时预估值 */
        System.out.println("threadGroup.activeCount() = " + threadGroup.activeCount());
        /** list()方法用于打印线程组threadGroup中所有的线程信息，对调试有一定帮助 */
        threadGroup.list();
    }
}

class Task implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        System.out.println("This is " + Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName());
        Thread.sleep(2000);
    }
}
