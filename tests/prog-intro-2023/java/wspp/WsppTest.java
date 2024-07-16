package wspp;

import base.Named;
import base.Selector;

import java.util.Comparator;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class WsppTest {
    private static final Named<Comparator<Map.Entry<String, Integer>>> INPUT = Named.of("", Comparator.comparingInt(e -> 0));

    private static final Named<IntFunction<IntStream>> ALL = Named.of("", size -> IntStream.range(0, size));
    private static final Named<Comparator<Map.Entry<String, Integer>>> SORTED = Named.of("Sorted", Map.Entry.comparingByKey());
    private static final Named<Comparator<Map.Entry<String, Integer>>> SORTED_R = Named.of("SortedR", Comparator.comparing(e -> reverse(e.getKey())));
    private static final Named<IntFunction<IntStream>> FIRST = Named.of("First", size -> IntStream.of(0));
    private static final Named<WsppTester.Extractor<Integer>> GLOBAL = Named.of("", (r, w, t, g) -> g);

    private static final Named<WsppTester.Extractor<String>> POSITION = Named.of("Position", (r, w, t, g) -> r + ":" + (t - w + 1));


    public static final Selector SELECTOR = new Selector(WsppTester.class)
            .variant("Base",            WsppTester.variant(INPUT,  ALL, Named.of("", (r, w, t, g) -> g)))
            .variant("Position",        WsppTester.variant(INPUT,  ALL, POSITION))
            .variant("SortedPosition",  WsppTester.variant(SORTED, ALL, POSITION))
            .variant("SortedFirst",     WsppTester.variant(SORTED, FIRST, GLOBAL))
            .variant("SortedRFirst",    WsppTester.variant(SORTED_R, FIRST, GLOBAL))
            ;

    private WsppTest() {
    }

    private static String reverse(final String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
