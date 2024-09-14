package thread.day3;

/**
 * 用于演示 t != (t = tail)
 */
public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) {
        int tail = 0, t = 5;
        if (t != (t = tail)) {
            System.out.println("t != (t = tail), t = " + t);
        }
    }
}
