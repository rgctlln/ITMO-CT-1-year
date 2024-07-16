package expression;

public class Divide extends Binary implements OwnExpression {

    public Divide(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "/";
    }

    @Override
    protected int calc(int a, int b) {
        return a / b;
    }

}