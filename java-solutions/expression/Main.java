package expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Subtract(
                new Multiply(
                        new Variable("x"),
                        new Variable("x")),
                new Add(new Multiply(
                        new Const(2),
                        new Variable("x")),
                        new Const(1))).evaluate(Integer.parseInt(args[0]))
        );
//        System.out.println(new Multiply(new Const(2), new Variable("x"))
//                .equals(new Multiply(new Variable("x"), new Const(2))));
    }
}
