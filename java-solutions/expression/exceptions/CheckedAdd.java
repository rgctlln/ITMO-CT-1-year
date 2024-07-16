package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedAdd extends Binary implements OwnExpression {
    public CheckedAdd(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "+";
    }

    @Override
    protected int calc(int a, int b) {
        int result = a + b;
        if (a > 0 && b > 0 && result <= 0 || a < 0 && b < 0 && result >= 0) {
            throw new MyException("overflow");
        } else {
            return result;
        }
    }
}
