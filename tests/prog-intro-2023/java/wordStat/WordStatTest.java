package wordStat;

import base.Named;
import base.Pair;
import base.Selector;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Tests for <a href="https://www.kgeorgiy.info/courses/prog-intro/homeworks.html#wordstat">Word Statistics</a> homework
 * of <a href="https://www.kgeorgiy.info/courses/prog-intro/">Introduction to Programming</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class WordStatTest {

    private static final Named<Function<String, Stream<String>>> ID  = Named.of("", Stream::of);

    private static final Named<Comparator<Pair<String, Integer>>> INPUT = Named.of("Input", Comparator.comparingInt(p -> 0));
    private static final Named<Comparator<Pair<String, Integer>>> COUNT = Named.of("Count", Comparator.comparingInt(Pair::second));

    public static final int SIZE = 3;

    static Named<Function<String, Stream<String>>> length(
            final String name,
            final int length,
            final Function<String, Stream<String>> lng,
            final Function<String, Stream<String>> shrt
    ) {
        return Named.of(name, s -> (s.length() >= length ? lng : shrt).apply(s));
    }

    private static final Named<Function<String, Stream<String>>> PREFIX_LONG =
            length("PrefixL", SIZE, s -> Stream.of(s.substring(0, SIZE)), s -> Stream.empty());
    private static final Named<Function<String, Stream<String>>> MIDDLE_LONG =
            length("MiddleL", 5, s -> Stream.of(s.substring(2, s.length() - 2)), s -> Stream.empty());
    private static final Named<Function<String, Stream<String>>> AFFIX_LONG =
            length(
                    "AffixL",
                    2,
                    s -> Stream.of(s.substring(0, 2), s.substring(s.length() - 2)),
                    s -> Stream.empty()
            );



    public static final Selector SELECTOR = new Selector(WordStatTester.class)
            .variant("Base",            WordStatTester.variant(INPUT, ID))
            .variant("CountPrefixL",    WordStatTester.variant(COUNT, PREFIX_LONG))
            .variant("Count",           WordStatTester.variant(COUNT, ID))
            .variant("CountMiddleL",    WordStatTester.variant(COUNT, MIDDLE_LONG))
            .variant("CountAffixL",     WordStatTester.variant(COUNT, AFFIX_LONG))
            ;

    private WordStatTest() {
        // Utility class
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
