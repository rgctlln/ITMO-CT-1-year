package game;

import java.io.PrintStream;
import java.util.Scanner;


public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
//            try {
//                final Move move = new Move(String.valueOf(Integer.parseInt(in.next())- 1)
//                        , String.valueOf(Integer.parseInt(in.next())- 1), cell);
//                if (position.isValid(move) == 1) {
//                    return move;
//                } else if (position.isValid(move) == 0) {
//                    final int row = Integer.parseInt(move.getRow());
//                    final int column = Integer.parseInt(move.getColumn());
//                    out.println("Move " + move + " is invalid");
//                }
//            } catch (NumberFormatException e) {
//                Move move = new Move("-1", "-1", cell);
//            }
//        }
//    }
//}
            try {
                String line = in.nextLine();
                Scanner in1 = new Scanner(line);
                int row = Integer.parseInt(in1.next()) - 1;
                int column = Integer.parseInt(in1.next()) - 1;
                if (in1.hasNext()) {
                    in1.close();
                }
                final Move move = new Move(String.valueOf(row), String.valueOf(column), cell);
                if (position.isValid(move) == 1) {
                    return move;
                } else {
                    out.println("Move " + move + " is invalid");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter numbers.");
            }
        }
    }
}
