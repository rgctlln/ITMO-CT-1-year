package expression;

public class Add extends Binary implements OwnExpression {

    public Add(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "+";
    }

    @Override
    protected int calc(int a, int b) {
        return a + b;
    }
}