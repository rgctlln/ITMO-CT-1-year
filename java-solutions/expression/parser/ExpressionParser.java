package expression.parser;

import expression.*;
import expression.exceptions.MyException;

public final class ExpressionParser extends BaseParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression) {
        this.source = new StringSource(expression);
        take();
        return parseBitwisePriority1();
    }

    private TripleExpression parseBitwisePriority1() {
        skipWhitespace();
        TripleExpression result = parseBitwisePriority2();
        skipWhitespace();
        while (isNextOperator('|')) {
            take();
            skipWhitespace();
            TripleExpression right = parseBitwisePriority2();
            result = new BitOr((OwnExpression) result, (OwnExpression) right);
        }
        return result;
    }

    private TripleExpression parseBitwisePriority2() {
        skipWhitespace();
        TripleExpression result = parseBitwisePriority3();
        skipWhitespace();
        while (isNextOperator('^')) {
            take();
            skipWhitespace();
            TripleExpression right = parseBitwisePriority3();
            result = new BitXOR((OwnExpression) result, (OwnExpression) right);
        }
        return result;
    }

    private TripleExpression parseBitwisePriority3() {
        skipWhitespace();
        TripleExpression result = parseExpression();
        skipWhitespace();
        while (isNextOperator('&')) {
            take();
            skipWhitespace();
            TripleExpression right = parseExpression();
            result = new BitAnd((OwnExpression) result, (OwnExpression) right);
        }
        return result;
    }

    private TripleExpression parseExpression() {
        skipWhitespace();
        TripleExpression result = parsePriority3();
        skipWhitespace();
        while (isNextOperator('+', '-')) {
            char operator = take();
            skipWhitespace();
            switch (operator) {
                case '+' -> {
                    TripleExpression right = parsePriority3();
                    result = new Add((OwnExpression) result, (OwnExpression) right);
                }
                case '-' -> {
                    TripleExpression right = parsePriority3();
                    result = new Subtract((OwnExpression) result, (OwnExpression) right);
                }
            }
        }
        return result;
    }

    private TripleExpression parsePriority3() {
        skipWhitespace();
        TripleExpression result = parsePriority2();
        skipWhitespace();
        while (true) {
            String operation = "";
            if (take('*')) {
                skipWhitespace();
                operation = "*";
                skipWhitespace();
            } else if (take('/')) {
                skipWhitespace();
                operation = "/";
                skipWhitespace();
            }
            if (!operation.isEmpty()) {
                switch (operation) {
                    case "*" -> {
                        skipWhitespace();
                        TripleExpression right = parsePriority2();
                        skipWhitespace();
                        result = new Multiply((OwnExpression) result, (OwnExpression) right);
                        break;
                    }
                    case "/" -> {
                        skipWhitespace();
                        TripleExpression right = parsePriority2();
                        skipWhitespace();
                        result = new Divide((OwnExpression) result, (OwnExpression) right);
                        break;
                    }
                }
                skipWhitespace();
            } else {
                break;
            }
        }
        return result;
    }

    private TripleExpression parsePriority2() {
        skipWhitespace();
        boolean isNegative = false;
        if (take('-')) {
            isNegative = true;
            skipWhitespace();
            char ch = take();
            skipWhitespace();
            if (Character.isDigit(ch)) {
                skipWhitespace();
                if (ch != '0') {
                    return new Const(parseNum(ch, isNegative));
                } else {
                    return new Negate(new Const(parseNum(ch, isNegative)));
                }
            } else if (Character.isLetter(ch)) {
                skipWhitespace();
                return new Negate(new Variable(parseVar(ch)));
            } else if (ch == '-') {
                skipWhitespace();
                return parsePriority2();
            } else if (ch == '(') {
                skipWhitespace();
                TripleExpression result = new Negate((OwnExpression) parseBitwisePriority1());
                expect(')');
                return result;
            } else {
                if (!eof()) {
                    skipWhitespace();
                }
            }
        }
        skipWhitespace();
        return parsePriority1();
    }

    private TripleExpression parsePriority1() {
        char ch = take();
        if (ch == '(') {
            TripleExpression result = parseBitwisePriority1();
            expect(')');
            return result;
        } else if (Character.isDigit(ch)) {
            return new Const(parseNum(ch, false));
        } else if (Character.isLetter(ch)) {
            return new Variable(parseVar(ch));
        }
        //throw error("Unexpected value.");
        return parseBitwisePriority1();
    }

    private int parseNum(char ch, boolean isNegative) {
        StringBuilder number = new StringBuilder();
        number.append(ch);
        while (between('0', '9')) {
            char nextChar = take();
            number.append(nextChar);
        }
        return parseLongAsInt(number.toString(), isNegative);
    }

    private String parseVar(char ch1) {
        StringBuilder var = new StringBuilder();
        var.append(ch1);
        if (between('a', 'z') || between('A', 'Z')) {
            char ch = take();
            while (between('a', 'z') || between('A', 'Z')) {
                var.append(ch);
//                if (!source.hasNext()) {
//                    break;
//                }
                ch = take();
            }
            var.append(ch);
        }
        return var.toString();
    }

    private int parseLongAsInt(String number, boolean isNegative) {
        try {
            long value = Long.parseLong(number);
            return (int) (isNegative ? -value : value);
        } catch (NumberFormatException e) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    private boolean isNextOperator(char... operators) {
        for (char op : operators) {
            if (test(op)) {
                return true;
            }
        }
        return false;
    }
}
