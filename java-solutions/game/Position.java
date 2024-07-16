package game;

public interface Position {
    int isValid(Move move);

    Cell getCell(int r, int c);
}
