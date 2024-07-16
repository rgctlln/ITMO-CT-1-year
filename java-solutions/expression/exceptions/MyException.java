package expression.exceptions;

public class MyException extends RuntimeException {
    String message;
    public MyException (String message) {
        super(message);
    }
}
