package src;

import java.util.ArrayList;
import java.util.List;

/**
 * main
 *
 * @author zcx
 * @date 2020/11/12
 */
public class StreamCollector {

    public static String lock = "";

    public static void main(String[] args) {
//        System.out.println(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(AverageCollector.get()));

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
