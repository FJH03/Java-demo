package thread.day1;

/**
 * 对32位操作系统进行long操作的时候，会因为读写不是原子性的，而产生问题
 */
public class MultiThreadLong {

    public static long t = 0;

    public static void main(String[] args) {
        new Thread(new WriteThread(111L)).start();
        new Thread(new WriteThread(-999L)).start();
        new Thread(new WriteThread(333L)).start();
        new Thread(new WriteThread(-444L)).start();
        new Thread(new ReadThread()).start();
    }
}

/** 赋值线程 */
class WriteThread implements Runnable {
    private long to;
    public WriteThread(long to) {
        this.to = to;
    }
    @Override
    public void run() {
        while (true) {
            MultiThreadLong.t = to;
            Thread.yield();
        }
    }
}

/** 读取线程 */
class ReadThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            long tmp = MultiThreadLong.t;
            // 由于long是64位的，在32位操作系统中，不是原子性操作，所以，if判断有可能是true
            if (tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L) {
                System.out.println(tmp);
            }
            Thread.yield();
        }
    }
}
