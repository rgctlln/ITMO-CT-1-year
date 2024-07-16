package expression;

public class Negate implements OwnExpression {
    private final OwnExpression expression;

    public Negate(OwnExpression expression) {
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
