package expression;

public abstract class Binary implements OwnExpression {
    private final OwnExpression var11;
    private final OwnExpression var21;

    public Binary(OwnExpression var11, OwnExpression var21) {
        this.var11 = var11;
        this.var21 = var21;
    }

    //protected abstract String getSign();

    @Override
    public int evaluate(int a) {
        return  calc(var11.evaluate(a) , var21.evaluate(a));

    }

    protected abstract int calc(int a, int b);
    protected abstract String getSign();

    @Override
    public int evaluate(int a, int b, int c) {
//        int result = 0;
//        switch (getOperation()) {
//            case "multiply" -> result = var11.evaluate(a, b, c) * var21.evaluate(a, b, c);
//            case "add" -> result = var11.evaluate(a, b, c) + var21.evaluate(a, b, c);
//            case "subtract" -> result = var11.evaluate(a, b, c) - var21.evaluate(a, b, c);
//            case "divide" -> result = var11.evaluate(a, b, c) / var21.evaluate(a, b, c);
//        }
//        return result;
        return calc(var11.evaluate(a, b, c), var21.evaluate(a, b, c));
    }

    @Override
    public String toString() {
        return "(" + var11 + " " + getSign() + " " + var21 + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this.getClass() == object.getClass()) {
            return var11.equals(((Binary) object).getLeft()) && var21.equals(((Binary) object).getRight());
        }
        return false;
    }

    public OwnExpression getLeft() {
        return var11;
    }

    public OwnExpression getRight() {
        return var21;
    }

    @Override
    public int hashCode() {
        return getRight().hashCode() * 3 + getLeft().hashCode() + getClass().hashCode();
    }
}
