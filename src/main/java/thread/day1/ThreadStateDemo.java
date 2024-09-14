package thread.day1;

/**
 * 查看线程状态
 */
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException{
        Thread thread = new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //线程执行完成之后，打印完成
            System.out.println("线程执行完毕");
        });

        System.out.println(thread.getState()); // NEW

        thread.start();

        System.out.println(thread.getState()); // RUNNABLE

        while(thread.getState() != Thread.State.TERMINATED){ //线程不是终止状态
            Thread.sleep(300);
            System.out.println(thread.getState()); //输出状态
        }
    }
}
