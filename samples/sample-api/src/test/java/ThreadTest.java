import org.junit.jupiter.api.Test;

public class ThreadTest {

    int num = 0;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 10; i1++) {
                    new Thread(() -> {
                        for (int i2 = 0; i2 < 10; i2++) {
                            new Thread(() -> num++).start();
                        }
                    }).start();
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(num++);
    }
}
