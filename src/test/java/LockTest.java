import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockTest {

    /**
     * 偏向4秒延迟，使用轻量级锁
     */
    @Test
    public void test1() {
        Object obj = new Object();
        logPrint(obj, "【1】未进入同步块，MarkWord为："); // non-biasable-无锁：十六进制最后一位的1=0001

        synchronized(obj) {
            logPrint(obj, "【2】进入同步块，MarkWord为："); // thin lock-轻量级锁：十六进制最后一位的0=0000
        }
    }

    /**
     * 超出偏向延迟，使用偏向锁
     */
    @Test
    @SneakyThrows
    public void test2() {
        TimeUnit.SECONDS.sleep(5); // 睡眠 5s

        Object obj = new Object();
        logPrint(obj, "【1】未进入同步块，MarkWord为："); // biasable-可偏向状态（101 + 没有具体线程）：十六进制最后一位的5=0101

        synchronized(obj) {
            logPrint(obj, "【2】进入同步块，MarkWord为："); // biased-已偏向状态（101 + 有具体线程）：在obj对象的MarkWord中已经存在线程id了
        }

        logPrint(obj, "【3】跳出同步块，MarkWord为："); // biased-跳出同步块，还是已偏向状态，即：线程id依然存在
    }

    /**
     * 锁升级：偏向锁升级为轻量级锁
     */
    @Test
    @SneakyThrows
    public void test3() {
        TimeUnit.SECONDS.sleep(5); // 睡眠 5s

        Object obj = new Object();
        logPrint(obj, "【1】未进入同步块，MarkWord为："); // biasable-可偏向状态（101 + 没有具体线程）：十六进制最后一位的5=0101

        synchronized (obj){
            logPrint(obj, "【2】进入同步块，MarkWord为："); // biased-已偏向状态（101 + 有具体线程）：在obj对象的MarkWord中已经存在线程id了
        }
        logPrint(obj, "【3】跳出同步块，MarkWord为："); // biased-跳出同步块，还是已偏向状态，即：线程id依然存在

        Thread t2 = new Thread(() -> {
            synchronized (obj) {
                logPrint(obj, "【4】新线程获取锁，MarkWord为："); // thin lock-轻量级锁：十六进制最后一位的0=0000
            }
        });

        t2.start();
        t2.join();
        logPrint(obj, "【5】跳出同步块，MarkWord为："); // non-biasable-无锁（001 + 没有具体线程）：十六进制最后一位的1=0001

        synchronized (obj){
            logPrint(obj, "【6】主线程再次进入同步块，MarkWord 为："); // thin lock-轻量级锁：十六进制最后一位的0=0000
        }

        logPrint(obj, "【7】跳出同步块，MarkWord为："); // non-biasable-无锁（001 + 没有具体线程）：十六进制最后一位的1=0001
    }

    /**
     * 【hashcode场景1】首先，生成hashcode，然后一个线程进入同步块，会发生什么呢？
     * 【结论就是】即便初始化为可偏向状态的对象，一旦调用Object#hashCode() 或者 System#identityHashCode(Object)，进入同步块就会直接使用轻量级锁
     */
    @Test
    @SneakyThrows
    public void test4() {
        TimeUnit.SECONDS.sleep(5); // 睡眠 5s

        Object obj = new Object();
        logPrint(obj, "【1】未生成 hashcode，MarkWord 为："); // biasable-可偏向状态（101 + 没有具体线程）：十六进制最后一位的5=0101

        obj.hashCode();
        logPrint(obj, "【2】已生成 hashcode，MarkWord 为："); // 无锁（001 + hashcode值）：十六进制最后一位的1=0001

        synchronized (obj){
            logPrint(obj, "【3】进入同步块，MarkWord 为："); // thin lock-轻量级锁：十六进制最后一位的0=0000
        }
    }

    /**
     * 【hashcode场景2】假如已偏向某一个线程，然后生成hashcode，然后同一个线程又进入同步块，会发生什么呢？
     * 【结论】会直接使用轻量级锁
     */
    @Test
    @SneakyThrows
    public void test5() {
        TimeUnit.SECONDS.sleep(5); // 睡眠 5s

        Object obj = new Object();
        logPrint(obj, "【1】未生成 hashcode，MarkWord 为："); // biasable-可偏向状态（101 + 没有具体线程）：十六进制最后一位的5=0101

        synchronized (obj) {
            logPrint(obj, "【2】进入同步块，MarkWord 为："); // biased-已偏向状态（101 + 有具体线程）：在obj对象的MarkWord中已经存在线程id了
        }

        obj.hashCode();
        logPrint(obj, "【3】生成 hashcode"); // 无锁状态

        synchronized (obj) {
            logPrint(obj, "【4】同一线程再次进入同步块，MarkWord 为："); // thin lock-轻量级锁：十六进制最后一位的8=1000
        }
    }

    /**
     * 【hashcode场景3】假如对象处于已偏向状态，在同步块中调用了生成hashcode的方法，会发生什么呢？
     * 【结论】如果对象处在已偏向状态，生成 hashcode 后，就会直接升级成重量级锁
     */
    @Test
    @SneakyThrows
    public void test6() {
        TimeUnit.SECONDS.sleep(5); // 睡眠 5s

        Object obj = new Object();
        logPrint(obj, "【1】未生成 hashcode，MarkWord 为："); // biasable-可偏向状态（101 + 没有具体线程）：十六进制最后一位的5=0101

        synchronized (obj){
            logPrint(obj,"【2】进入同步块，MarkWord 为："); // biased-已偏向状态（101 + 有具体线程）：在obj对象的MarkWord中已经存在线程id了

            obj.hashCode();

            logPrint(obj,"【3】已偏向状态下，生成 hashcode，MarkWord 为："); // fat lock-重量级锁：十六进制最后一位的a=1010
        }
    }

    /**
     * 【hashcode场景4】假如对象处于轻量级锁的状态，在同步块中调用了生成hashcode的方法，会发生什么呢？
     * 【结论】如果对象处在轻量级锁的状态，生成 hashcode 后，就会直接升级成重量级锁
     */

    @Test
    @SneakyThrows
    public void test7() {
        Object obj = new Object();
        logPrint(obj, "【1】未进入同步块，MarkWord为："); // non-biasable-无锁：十六进制最后一位的1=0001

        synchronized(obj) {
            logPrint(obj, "【2】进入同步块，MarkWord为："); // thin lock-轻量级锁：十六进制最后一位的0=0000

            obj.hashCode();

            logPrint(obj,"【3】已偏向状态下，生成 hashcode，MarkWord 为："); // fat lock-重量级锁：十六进制最后一位的a=1010
        }
    }

    public void logPrint(Object obj, String info) {
        log.info(info);
        log.info(ClassLayout.parseInstance(obj).toPrintable());
    }
}
