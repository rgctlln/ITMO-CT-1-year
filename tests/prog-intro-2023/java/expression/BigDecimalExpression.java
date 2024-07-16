package expression;

import base.Pair;
import base.TestCounter;
import expression.common.ExpressionKind;
import expression.common.Node;
import expression.common.Type;

import java.math.BigDecimal;
import java.util.List;

import static expression.common.Node.constant;
import static expression.common.Node.op;

/**
 * One-argument arithmetic expression over {@link BigDecimals}.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
@SuppressWarnings("ClassReferencesSubclass")
public interface BigDecimalExpression extends ToMiniString {
    Type<BigDecimal> TYPE = new Type<>(
            v -> new BigDecimal(v + ".000"),
            random -> BigDecimal.valueOf(random.getRandom().nextGaussian()),
            BigDecimal.class
    );
    ExpressionKind<BigDecimalExpression, BigDecimal> KIND = new ExpressionKind<>(
            TYPE,
            BigDecimalExpression.class,
            List.of(Pair.of("x", new Variable("x"))),
            (expr, variables, values) -> expr.evaluate(values.get(0))
    );

    BigDecimal evaluate(BigDecimal x);

    static ExpressionTester<?, ?> tester(final TestCounter counter) {
        final Variable vx = new Variable("x");
        final Node<BigDecimal> nx = op("x");

        final Node<BigDecimal> n1 = constant(BigDecimal.ONE);
        final Node<BigDecimal> n10 = constant(BigDecimal.TEN);
        final Const c1 = TYPE.constant(BigDecimal.ONE);
        final Const c10 = TYPE.constant(BigDecimal.TEN);

        return new ExpressionTester<>(
                counter, KIND, c -> x -> c,
                (op, a, b) -> x -> op.apply(a.evaluate(x), b.evaluate(x)),
                BigDecimal::add, BigDecimal::subtract, BigDecimal::multiply, BigDecimal::divide
        )
                .basic("10", "10", x -> BigDecimal.TEN, TYPE.constant(BigDecimal.TEN))
                .basic("x", "x", x -> x, vx)
                .basic(op("+", nx, n10), new Add(vx, c10))
                .basic(op("-", n1, nx), new Subtract(c1, vx))
                .basic(op("*", n10, nx), new Multiply(c10, vx))
                .basic(op("+", nx, nx), new Add(vx, vx))
                .basic(op("/", nx, n10), new Divide(vx, c10));
    }

    static void main(final String... args) {
        TripleExpression.SELECTOR
                .variant("BigDecimal", ExpressionTest.v(BigDecimalExpression::tester))
                .main(args);
    }
}
