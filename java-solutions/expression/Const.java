package expression;

public class Const implements OwnExpression {
    private final int const1;

    public Const(int const1) {
        this.const1 = const1;
    }

    @Override
    public int evaluate(int a) {
        return const1;
    }
    @Override
    public int evaluate(int a, int b, int c) {
        return const1;
    }

    @Override
    public String toString() {
        return String.valueOf(const1);
    }

    public int getLeft() {
        return const1;
    }

    public int getRight() {
        return const1;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() == Const.class) {
            return const1 == ((Const) object).getRight();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return const1 * 69;
    }
}