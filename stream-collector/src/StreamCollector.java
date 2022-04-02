package src;

import java.util.stream.Stream;

/**
 * main
 *
 * @author zcx
 * @date 2020/11/12
 */
public class StreamCollector {

    public static String lock = "";

    public static void main(String[] args) {
        System.out.println(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(AverageCollector.get()));
    }
}
