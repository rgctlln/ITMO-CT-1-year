package sum;

import base.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class SumTest {
    @FunctionalInterface
    /* package-private */ interface Op<T extends Number> extends UnaryOperator<SumTester<T>> {}

    private static BiConsumer<Number, String> approximate(final Function<String, Number> parser, final double precision) {
        return (expected, out) ->
                Asserts.assertEquals("Sum", expected.doubleValue(), parser.apply(out).doubleValue(), precision);
    }

    private static final BiConsumer<Number, String> TO_STRING = (expected, out) -> Asserts.assertEquals("Sum", expected.toString(), out);

    private static final Named<Supplier<SumTester<Integer>>> BASE = Named.of("", () -> new SumTester<>(
            Integer::sum, n -> (int) n, (r, max) -> r.nextInt() % max, TO_STRING,
            10, 100, Integer.MAX_VALUE
    ));

    private static final Named<Supplier<SumTester<Double>>> DOUBLE = Named.of("Double", () -> new SumTester<>(
            Double::sum, n -> (double) n, (r, max) -> (r.getRandom().nextDouble() - 0.5) * 2 * max,
            approximate(Double::parseDouble, 1e-10),
            10.0, 0.01, 1e20, 1e100, Double.MAX_VALUE / 10000)
            .test(5, "2.5 2.5")
            .test(0, "1e100 -1e100")
            .testT(2e100, "1.5e100 0.5e100"));

    private static final Named<Supplier<SumTester<Long>>> LONG = Named.of("Long", () -> new SumTester<>(
            Long::sum, n -> n, (r, max) -> r.getRandom().nextLong() % max, TO_STRING,
            10L, 100L, (long) Integer.MAX_VALUE, Long.MAX_VALUE)
            .test(12345678901234567L, " +12345678901234567 ")
            .test(0L, " +12345678901234567 -12345678901234567")
            .test(0L, " +12345678901234567 -12345678901234567"));

    private static final Named<Supplier<SumTester<BigInteger>>> BIG_INTEGER = Named.of("BigInteger", () -> new SumTester<>(
            BigInteger::add, BigInteger::valueOf, (r, max) -> new BigInteger(max.bitLength(), r.getRandom()), TO_STRING,
            BigInteger.TEN, BigInteger.TEN.pow(10), BigInteger.TEN.pow(100), BigInteger.TWO.pow(1000))
            .test(0, "10000000000000000000000000000000000000000 -10000000000000000000000000000000000000000"));

    /* package-private */ static <T extends Number> Named<Op<T>> plain() {
        return Named.of("", test -> test);
    }

    /* package-private */ static <T extends Number> Consumer<TestCounter> variant(
            final Named<Function<String, Runner>> runner,
            final Named<Supplier<SumTester<T>>> test,
            final Named<? extends Function<? super SumTester<T>, ? extends SumTester<?>>> modifier
    ) {
        return counter -> modifier.getValue().apply(test.getValue().get())
                .test("Sum" + test.getName() + modifier.getName() + runner.getName(), counter, runner.getValue());
    }

    private static <T extends Number> Named<Op<T>> compose(
            final String prefix,
            final Named<Op<T>> inner,
            final Op<T> outer
    ) {
        return Named.of(prefix + inner.getName(), t -> outer.apply(inner.getValue().apply(t)));
    }

    private static <T extends Number> Named<Op<T>> space(final Named<Op<T>> inner) {
        return compose("Space", inner, t -> t.setSpaces(List.of(" \u2000\u2001\u2002\u2003\u00A0")));
    }

    private static final Map<Integer, List<String>> LOCAL_DIGITS = IntStream.range(0, Character.MAX_VALUE)
            .filter(Character::isDigit)
            .boxed()
            .collect(Collectors.groupingBy(
                    c -> (int) "0123456789".charAt(Character.getNumericValue(c)),
                    Collectors.mapping(c -> String.valueOf((char) c.intValue()), Collectors.toList())
            ));

    private static <T extends Number> Named<Op<T>> local(final Named<Op<T>> inner) {
        return compose("", inner, test -> test.setToString((r, n) -> n.toString().chars()
                .mapToObj(c -> {
                    final List<String> items = LOCAL_DIGITS.get(c);
                    return items == null ? String.valueOf((char) c) : r.randomItem(items);
                })
                .collect(Collectors.joining())));
    }

    /* package-private */ static final Named<Function<String, Runner>> RUNNER =
            Named.of("", Runner.packages("", "sum")::args);


    public static final Selector SELECTOR = selector(SumTest.class, RUNNER);

    private SumTest() {
        // Utility class
    }

    public static Selector selector(final Class<?> owner, final Named<Function<String, Runner>> runner) {
        return new Selector(owner)
                .variant("Base",            variant(runner, BASE, plain()))
                .variant("Double",          variant(runner, DOUBLE, plain()))
                .variant("DoubleSpace",     variant(runner, DOUBLE, space(plain())))
                .variant("LongSpace",       variant(runner, LONG, space(plain())))
                .variant("BigIntegerSpace", variant(runner, BIG_INTEGER, space(local(plain()))))
                ;
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
