package thread.day3;

/**
 * 测试retry
 *
 * retry就是一个标记，标记对一个循环方法的操作continue/break处理点，功能类似于goto，所以retry一般都是伴随着for循环出现，
 * retry:标记的下一行就是for循环，在for循环里面调用continue/break再紧接着retry标记时，就表示从这个地方开始执行continue/break操作
 *
 * Author muse
 */
public class TestRetry {
    public static void main(String[] args) {
        case1();
        case2();
        case3();
        case4();
        case5();
        case6();
    }

    public static void case1() {
        System.out.println("-------------case1 for all---------------");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }

    public static void case2() {
        System.out.println("-------------case2 break---------------");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    break;
                }
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }

    public static void case3() {
        System.out.println("-------------case3 continue---------------");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue;
                }
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }

    public static void case4() {
        System.out.println("-------------case4 continue+retry---------------");
        retry:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue retry;
                }
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }

    public static void case5() {
        System.out.println("-------------case5 break+retry---------------");
        retry:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    break retry;
                }
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }

    public static void case6() {
        System.out.println("-------------case6 break+muse---------------");
        muse:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    break muse;
                }
                System.out.println("batchNums=" + i + " nums=" + j);
            }
        }
    }
}
