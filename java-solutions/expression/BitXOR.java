package expression;

public class BitXOR extends Binary implements OwnExpression {
    public BitXOR(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected int calc(int a, int b) {
        return a ^ b;
    }

    @Override
    protected String getSign() {
        return "^";
    }
}
