package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedDivide extends Binary implements OwnExpression {
    public CheckedDivide(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "/";
    }

    @Override
    protected int calc(int a, int b) {
        if (b != 0) {
            return a / b;
        } else {
            throw new MyException("divide by zero");
        }
    }
}
