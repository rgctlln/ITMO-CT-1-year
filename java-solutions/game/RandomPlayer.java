package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            String r = String.valueOf(random.nextInt(3));
            String c = String.valueOf(random.nextInt(3));
            final Move move = new Move(r, c, cell);
            if (position.isValid(move) == 1) {
                return move;
            } else {
                final Move move1 = new Move(r, c, cell);
            }
        }
    }
}
