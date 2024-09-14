package thread.day1;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

/**
 * 测试调用stop方法结束线程的不安全性
 */
public class StopThread {
    public static User user = new User();
    @SneakyThrows
    public static void main(String[] args) {
        new ReadObjectThread().start(); // 开启读线程
        while (true) { // 开启写线程
            Thread changeObjectThread = new ChangedObjectThread();
            changeObjectThread.start();
            Thread.sleep(150);
            changeObjectThread.stop(); // 执行stop，强制终止changeObjectThread线程，并且没有任何错误中断信息输出
        }
    }

    /** 改变变量值的线程 */
    public static class ChangedObjectThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                synchronized(user) {
                    int v = (int) (System.currentTimeMillis()/1000);
                    user.setId(v);
                    Thread.sleep(60); // 强制要求沉睡60毫秒
                    user.setNo(v);
                }
                Thread.yield();
            }
        }
    }

    /** 读取变量值的线程 */
    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized(user) {
                    if (user.getId() != user.getNo()) { // 只有当user对象的id和no不同的时候，才输出
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }
    }
}

@Data
@ToString
class User {
    private int id = 0;
    private int no = 0;
}