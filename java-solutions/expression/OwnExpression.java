package expression;

public interface OwnExpression extends Expression, TripleExpression {
    int evaluate(int a);

    String toString();

    int evaluate(int a, int b, int c);
}
