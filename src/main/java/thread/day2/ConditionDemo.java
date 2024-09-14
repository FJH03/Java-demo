package thread.day2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试Condition
 * <p>
 * [相同点]
 *  Condition和Object.wait()、Object.notify()方法的作用是大致相同的
 *  Condition.await()--->Object.wait()
 *  Condition.signal()--->Object.notify()
 *  Condition.signalAll()--->Object.notifyAll()
 * [不同点]
 *  Object.wait()和Object.notify()方法是和Synchronized关键字合作使用的
 *  Condition是与ReentrantLock相关联的。
 * <p>
 * [重要方法]
 *  await()：使当前线程等待，同时释放当前锁。
 *  awaitUninterruptibly()：与await()方法类似，但是它并不会在等待过程中响应中断。
 *  signal()： 用于唤醒一个正在等待中的线程。
 *  signalAll()：唤醒所有等待中的线程。
 */
public class ConditionDemo {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition(); // 通过Lock接口获得Condition实例对象

    public static void main(String[] args) throws Throwable {
        new Thread(() -> {
            try {
                lock.lock();
                print("子线程调用了lock()方法，获得了锁；调用condition.await()方法，释放了锁，开始等待");
                condition.await(); /** 释放当前锁，进入等待中；其中，调用await之前，一定要先获得锁 */
                print("子线程获得锁，继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                print("子线程调用了lock.unlock()，执行了解锁操作");
            }
        }).start();

        Thread.sleep(1000);
        print("主线程沉睡了1秒钟");

        lock.lock(); /** 调用signal之前，一定要先获得锁，否则会报IllegalMonitorStateException异常 */
        print("主线程调用了lock()方法，获得了锁");

        condition.signal();
        print("主线程调用了condition.signal()方法，来唤醒子线程");

        Thread.sleep(2000);
        print("主线程沉睡了2秒钟");

        lock.unlock(); /** 必须解锁，子线程才能继续往下走 */
        print("主线程调用了lock.unlock()方法，执行了解锁操作，需要注意的是：必须解锁，子线程才能继续往下走");
    }

    private static void print(String msg) {
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + "，" + msg);
    }
}

