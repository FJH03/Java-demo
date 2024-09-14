package thread.day1;

/**
 * interrupt()      设置中断状态
 * interrupted()    判断是否被中断，并清除当前中断状态
 * isInterrupted()  只是判断是否被中断，并不清除中断状态
 *
 * @author: muse
 **/
public class InterruptDemo {
    public static void main(String[] args) throws Throwable {
        // test1();
        // test2();
        // test3();
        test4();
    }

    public static void test1() throws Throwable {
        Thread thread = new Thread(()-> {
            for (;;) {
                /** isInterrupted()：只是判断是否被中断，并不清除中断状态 */
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("InterruptDemo is isInterrupted check once!");
                }
                /** 因为没有清除中断状态，所以第二次再次执行isInterrupted()判断依然为true */
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("InterruptDemo is isInterrupted check twice!");
                    break;
                }
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(500);
        /** interrupt()：设置中断状态 */
        thread.interrupt();
    }

    public static void test2() throws Throwable {
        Thread thread = new Thread(()-> {
            for (;;) {
                /** interrupted()：判断是否被中断，并清除当前中断状态 */
                if (Thread.currentThread().interrupted()) {
                    System.out.println("InterruptDemo is interrupted check once!");
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("InterruptDemo is isInterrupted check twice!");
                    break;
                }
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

    public static void test3() throws Throwable {
        Thread thread = new Thread(()-> {
            for (;;) {
                /** 因为异常清除了中断状态，所以此处判断为false */
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("InterruptDemo is isInterrupted!");
                    break;
                }
                try {
                    /** 如果中断sleep方法，会抛出InterruptedException异常，并清除中断状态 */
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("InterruptDemo is InterruptedException!");
                }
            }
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

    public static void test4() throws Throwable {
        Thread thread = new Thread(()-> {
            for (;;) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("InterruptDemo is isInterrupted!");
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("InterruptDemo is InterruptedException!");
                    /** 因为中断标识被异常清除了，所以再次设置中断标识，这样第二次循环的时候，isInterrupted判断就可以break */
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
