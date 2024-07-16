package expression;

public class Multiply extends Binary implements OwnExpression {

    public Multiply(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "*";
    }

    @Override
    protected int calc(int a, int b) {
        return a * b;
    }

}