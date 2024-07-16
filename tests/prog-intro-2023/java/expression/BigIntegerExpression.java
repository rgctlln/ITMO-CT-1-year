package expression;

import base.Pair;
import base.TestCounter;
import expression.common.ExpressionKind;
import expression.common.Node;
import expression.common.Type;

import java.math.BigInteger;
import java.util.List;

import static expression.common.Node.constant;
import static expression.common.Node.op;

/**
 * One-argument arithmetic expression over {@link BigIntegers}.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
@SuppressWarnings("ClassReferencesSubclass")
public interface BigIntegerExpression extends ToMiniString {
    Type<BigInteger> TYPE = new Type<>(
            BigInteger::valueOf,
            random -> BigInteger.valueOf(random.getRandom().nextLong()),
            BigInteger.class
    );
    ExpressionKind<BigIntegerExpression, BigInteger> KIND = new ExpressionKind<>(
            TYPE,
            BigIntegerExpression.class,
            List.of(Pair.of("x", new Variable("x"))),
            (expr, variables, values) -> expr.evaluate(values.get(0))
    );

    BigInteger evaluate(BigInteger x);

    static ExpressionTester<?, ?> tester(final TestCounter counter) {
        final Variable vx = new Variable("x");
        final Node<BigInteger> nx = op("x");
        final Node<BigInteger> n1 = constant(BigInteger.ONE);
        final Node<BigInteger> n2 = constant(BigInteger.TWO);
        final Const c2 = TYPE.constant(BigInteger.TWO);
        final Const c1 = TYPE.constant(BigInteger.ONE);

        return new ExpressionTester<>(
                counter, KIND, c -> x -> c,
                (op, a, b) -> x -> op.apply(a.evaluate(x), b.evaluate(x)),
                BigInteger::add, BigInteger::subtract, BigInteger::multiply, BigInteger::divide
        )
                .basic("10", "10", x -> BigInteger.TEN, KIND.constant(BigInteger.TEN))
                .basic("x", "x", x -> x, vx)
                .basic(op("+", nx, n2), new Add(vx, c2))
                .basic(op("-", n1, nx), new Subtract(c1, vx))
                .basic(op("*", n2, nx), new Multiply(c2, vx))
                .basic(op("+", nx, nx), new Add(vx, vx))
                .basic(op("/", nx, n2), new Divide(vx, c2));
    }

    static void main(final String... args) {
        TripleExpression.SELECTOR
                .variant("BigInteger", ExpressionTest.v(BigIntegerExpression::tester))
                .main(args);
    }
}
