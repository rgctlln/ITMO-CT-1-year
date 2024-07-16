package expression;

public class Variable implements OwnExpression {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int a) {
        return a;
    }
    @Override
    public int evaluate(int a, int b, int c) {
        if (var.equals("x")) {
            return a;
        }
        if (var.equals("y")) {
            return b;
        } else {
            return c;
        }
    }

    @Override
    public String toString() {
        return var;
    }

    public String getLeft() {
        return var;
    }

    public String getRight() {
        return var;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() == Variable.class) {
            return var.equals(((Variable) object).getLeft());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }
}