package game;

import java.util.Random;

public class RandomMNKPlayer implements Player {
    private final Random random;
    private final int n;
    private final int m;

    public RandomMNKPlayer(final Random random, int n, int m) {
        this.random = random;
        this.n = n;
        this.m = m;
    }

    public RandomMNKPlayer(int m, int n) {
        this(new Random(), m, n);
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        //while (true) {
            String r = String.valueOf(random.nextInt(n));
            String c = String.valueOf(random.nextInt(m));
        //if (position.isValid(move) == 1) {
            return new Move(r, c, cell);
            //}

//            } else {
//                final Move move1 = new Move(r, c, cell);
//            }
       // }
    }
}
