package expression.parser;

import expression.*;
import expression.exceptions.CheckedAdd;
import expression.exceptions.CheckedDivide;
import expression.exceptions.CheckedNegate;
import expression.exceptions.CheckedSubtract;

public class Test {
    public static void main(String[] args) {
        String str = "0 & 0";
        CharSource source = new StringSource(str);
//        ExpressionParser parser = new ExpressionParser();
//        System.out.println(parser.parse(str));
        //System.out.println(new CheckedDivide(new Const(10), new Const(0)).evaluate(1));
//        System.out.println(new Multiply(new Const(Integer.MIN_VALUE),
//                new Const(-20)).evaluate(1));
//        System.out.println(new Negate(new Const(Integer.MIN_VALUE - 1)).evaluate(1));
        System.out.println(new CheckedSubtract(new Const(Integer.MIN_VALUE), new Const(Integer.MIN_VALUE + 1)).evaluate(1));
        //System.out.println(new CheckedAdd(new Const(Integer.MIN_VALUE + 1), new Const(Integer.MIN_VALUE)).evaluate(1));
    }
}
