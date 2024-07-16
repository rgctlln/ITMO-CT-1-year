package expression.parser;

import expression.ToMiniString;
import expression.common.ExpressionKind;
import expression.common.Reason;

import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class Operations {
    public static final Operation NEGATE = unary("-", a -> -a);
    @SuppressWarnings("Convert2MethodRef")
    public static final Operation ADD       = binary("+", 1600, (a, b) -> a + b);
    public static final Operation SUBTRACT  = binary("-", 1602, (a, b) -> a - b);
    public static final Operation MULTIPLY  = binary("*", 2001, (a, b) -> a * b);
    public static final Operation DIVIDE    = binary("/", 2002, (a, b) -> b == 0 ? Reason.DBZ.error() : a / b);

    public static final Operation AND = binary("&", 800, (a, b) -> a & b);
    public static final Operation XOR = binary("^", 760, (a, b) -> a ^ b);
    public static final Operation OR = binary("|", 720, (a, b) -> a | b);

    public static final Operation L_ONES = unary("l1", v ->
            (int) IntStream.iterate((int) v, i -> i < 0, i -> i << 1).count());
    public static final Operation T_ONES = unary("t1", v ->
            (int) IntStream.iterate((int) v, i -> (i & 1) != 0, i -> i >>> 1).count());

    public static final Operation HIGH = unary("high", v -> Integer.highestOneBit((int) v));
    public static final Operation LOW = unary("low", v -> Integer.lowestOneBit((int) v));

    public static final Operation NOT = unary("~", a -> ~a);

    public static final Operation L_ZEROES = unary("l0", v -> Integer.numberOfLeadingZeros((int) v));
    public static final Operation T_ZEROES = unary("t0", v -> Integer.numberOfTrailingZeros((int) v));

    public static final Operation MIN = binary("min", 401, Math::min);
    public static final Operation MAX = binary("max", 401, Math::max);

    @SuppressWarnings("IntegerMultiplicationImplicitCastToLong")
    public static final Operation SHIFT_L = binary("<<", 1202, (a, b) -> (int) a << (int) b);
    public static final Operation SHIFT_A = binary(">>", 1202, (a, b) -> (int) a >> (int) b);
    public static final Operation SHIFT_R = binary(">>>", 1202, (a, b) -> (int) a >>> (int) b);


    private static Operation checkedLogN(final String name, final DoubleUnaryOperator op) {
        return unary(name, NEG_LOG.less(1, a -> (long) op.applyAsDouble(a)));
    }
    private static final Reason NEG_LOG = new Reason("Logarithm of negative value");
    public static final Operation CHECKED_LOG_2 = checkedLogN("log2", a -> Math.log(a) / Math.log(2));

    private static Operation checkedPowN(final String name, final int base, final int limit) {
        return unary(name, NEG_POW.less(0, Reason.OVERFLOW.greater(limit, a -> (long) Math.pow(base, a))));
    }
    private static final Reason NEG_POW = new Reason("Exponentiation to negative power");
    public static final Operation CHECKED_POW_2 = checkedPowN("pow2", 2, 31);


    private Operations() {
    }

    @FunctionalInterface
    public interface Operation extends Consumer<ParserTester> {}

    public static Operation unary(final String name, final LongUnaryOperator op) {
        return tests -> tests.unary(name, op);
    }

    public static Operation binary(final String name, final int priority, final LongBinaryOperator op) {
        return tests -> tests.binary(name, priority, op);
    }

    public static <E extends ToMiniString, C> Operation kind(
            final ExpressionKind<E, C> kind,
            final ParserTestSet.Parser<E> parser
    ) {
        return factory -> factory.kind(kind, parser);
    }
}
