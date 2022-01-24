import java.util.ArrayList;
import java.util.List;

public class Test {
    public static String lock = "";
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add("t1     "+i);
                    System.out.println("t1     "+i);
                }
                System.out.println("t1 end");
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                for (int i = 100; i < 200; i++) {

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add("t2     "+i);
                    System.out.println("t2     "+i);
                }
                System.out.println("t2 end");
            }
        }).start();
    }
}
