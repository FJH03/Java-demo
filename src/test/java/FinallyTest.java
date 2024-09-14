import org.junit.jupiter.api.Test;

public class FinallyTest {

    /**
     * 测试finally和return的执行顺序
     *
     * finally语句是在try的return语句执行之后，return返回之前执行。
     */
    @Test
    public void test() {
        int b = test1();
    }

    private int test1() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            System.out.println("b>25, b=" + b);
        }
        return b;
    }
}