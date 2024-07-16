package reverse;

import base.ExtendedRandom;
import base.Named;
import base.Selector;
import base.TestCounter;
import reverse.ReverseTester.Op;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * Tests for {@code Reverse} homework.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class ReverseTest {
    @FunctionalInterface
    private interface IntToLongBinaryOperator {
        long applyAsLong(int a, int b);
    }

    private static final Named<Op> REVERSE = Named.of("", ReverseTester::transform);

    public static final int MAX_SIZE = 10_000 / TestCounter.DENOMINATOR;

    private static final Named<Op> SUM = scan2("Sum", 0, n -> n, Long::sum);

    private static final Named<Op> MIN_R = scan2("MinR", Integer.MAX_VALUE, (a, b) -> b, Math::min);

    private static final Named<Op> MIN_C = scan2("MinC", Integer.MAX_VALUE, Math::min, (a, b) -> b);

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> HEX = Named.of("Hex", (r, i) -> Integer.toHexString(i));

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> HEX_DEC = Named.of("HexDec", (r, i) ->
            r.nextBoolean() ? Integer.toString(i) : toHexString(r, i));

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> DEC = Named.of("", (r, i) -> Integer.toString(i));

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> HEX_ABC = Named.of("HexAbc", (r, i) ->
            r.nextBoolean() ? toAbc(i) : toHexString(r, i));

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> HEX_DEC_ABC = Named.of("HexDecAbc", (r, i) ->
            r.nextInt(3) == 0 ? toAbc(i) :
            r.nextBoolean() ? Integer.toString(i) : toHexString(r, i));

    private static final Named<BiFunction<ExtendedRandom, Integer, String>> ABC = Named.of("Abc", (r, i) -> toAbc(i));

    public static final Selector SELECTOR = selector(ReverseTest.class, MAX_SIZE);

    private ReverseTest() {
        // Utility class
    }

    private static Named<Op> scan2(
            final String name,
            final int zero,
            final IntToLongFunction map,
            final LongBinaryOperator reduce
    ) {
        return Named.of(name, ints -> scan2(ints, zero, map, reduce));
    }

    private static long[][] scan2(
            final int[][] ints,
            final int zero,
            final IntToLongFunction map,
            final LongBinaryOperator reduce
    ) {
        return scan2(ints, zero, map, reduce, reduce);
    }

    private static long[][] scan2(
            final int[][] ints,
            final int zero,
            final IntToLongFunction map,
            final LongBinaryOperator reduceC,
            final LongBinaryOperator reduceR
    ) {
        // This code is intentionally obscure
        final long[] cs = new long[Arrays.stream(ints).mapToInt(r -> r.length).max().orElse(0)];
        Arrays.fill(cs, zero);
        //noinspection NestedAssignment
        return Arrays.stream(ints)
                .map(i -> scan(i, (j, n) -> cs[j] = reduceC.applyAsLong(cs[j], map.applyAsLong(n)), reduceR))
                .toArray(long[][]::new);
    }

    private static long[] scan(final int[] input, final IntToLongBinaryOperator map, final LongBinaryOperator reduce) {
        final long[] ints = IntStream.range(0, input.length).mapToLong(i -> map.applyAsLong(i, input[i])).toArray();
        Arrays.parallelPrefix(ints, reduce);
        return ints;
    }

    private static Named<Op> scan2(
            final String name,
            final int zero,
            final LongBinaryOperator reduceC,
            final LongBinaryOperator reduceR
    ) {
        return Named.of(name, ints -> scan2(ints, zero, n -> n, reduceC, reduceR));
    }

    private static String toHexString(final ExtendedRandom r, final Integer i) {
        return (r.nextBoolean() ? "0x" : "0X") + Integer.toHexString(i);
    }

    private static String toAbc(final int value) {
        final char[] chars = Integer.toString(value).toCharArray();
        for (int i = value < 0 ? 1 : 0; i < chars.length; i++) {
            //noinspection ImplicitNumericConversion
            chars[i] += 49;
        }
        return new String(chars);
    }

    public static Selector selector(final Class<?> owner, final int maxSize) {
        return new Selector(owner)
                .variant("Base",        ReverseTester.variant(maxSize, REVERSE))
                .variant("MinR",        ReverseTester.variant(maxSize, MIN_R))
                .variant("MinC",        ReverseTester.variant(maxSize, MIN_C))
                .variant("MinRAbc",     ReverseTester.variant(maxSize, "", MIN_R, ABC, ABC))
                .variant("MinCAbc",     ReverseTester.variant(maxSize, "", MIN_C, ABC, ABC))
                .variant("SumHex",      ReverseTester.variant(maxSize, "", SUM, HEX, HEX))
                .variant("SumHexDec",   ReverseTester.variant(maxSize, "", SUM, HEX_DEC, DEC))
                .variant("SumHexAbc",   ReverseTester.variant(maxSize, "", SUM, HEX_ABC, ABC))
                .variant("SumHexDecAbc",ReverseTester.variant(maxSize, "", SUM, HEX_DEC_ABC, ABC))
                ;
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
