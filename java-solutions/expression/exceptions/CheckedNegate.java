package expression.exceptions;

import expression.OwnExpression;

public class CheckedNegate implements OwnExpression {
    private final OwnExpression expression;

    public CheckedNegate(OwnExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int a) {
        return -(expression.evaluate(a));
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return -(expression.evaluate(a, b, c));
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }
}
