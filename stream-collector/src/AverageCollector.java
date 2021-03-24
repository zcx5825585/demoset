package src;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author zcx
 * @date 2020/11/12
 */
public class AverageCollector implements Collector<Integer, Integer[], Double> {

    private static AverageCollector averageCollector = new AverageCollector();

    private AverageCollector() {
    }

    public static AverageCollector get() {
        return averageCollector;
    }

    @Override
    public Supplier<Integer[]> supplier() {
        return () -> new Integer[]{0, 0};
    }

    @Override
    public BiConsumer<Integer[], Integer> accumulator() {
        return (arr, num) -> {
            arr[0] += num;
            arr[1] += 1;
        };
    }

    @Override
    public BinaryOperator<Integer[]> combiner() {
        return (arr1, arr2) -> {
            arr1[0] += arr2[0];
            arr1[1] += arr2[1];
            return arr1;
        };
    }

    @Override
    public Function<Integer[], Double> finisher() {
        return arr -> arr[0] * 1.0d / arr[1];
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.singleton(Characteristics.UNORDERED);
    }
}
