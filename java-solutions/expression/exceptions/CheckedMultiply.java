package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedMultiply extends Binary implements OwnExpression {
    public CheckedMultiply(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "*";
    }

    @Override
    protected int calc(int a, int b) {
        if (a < 0 && b < 0 && a * b != 0) {
            return a * b;
        } else if (a > 0 && b > 0 && a * b > 0) {
            return a * b;
        } else {
            throw new MyException("overflow");
        }
    }
}
