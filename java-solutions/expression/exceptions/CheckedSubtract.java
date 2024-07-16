package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedSubtract extends Binary implements OwnExpression {
    public CheckedSubtract(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "-";
    }

    @Override
    protected int calc(int a, int b) {
        int result = a - b;
        if (b <= 0 && result <= 0 && b >= a) {
            return result;
        } else {
            throw new MyException("overflow");
        }
    }
}
