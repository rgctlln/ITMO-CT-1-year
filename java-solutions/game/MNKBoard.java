package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private final Cell[][] cells;
    private final int m;
    private final int n;
    private final int k;
    private Cell turn;
    private int empty;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        this.empty = m * n;
    }


    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (isValid(move) == -1) {
            return Result.LOSE;
        }
        cells[Integer.parseInt(move.getRow())][Integer.parseInt(move.getColumn())] = move.getValue();
        empty--;
        int r = Integer.parseInt(move.getRow());
        int c = Integer.parseInt(move.getColumn());
        int countRowForward = 1;
        int countRowBack = 1;
        int countColumnTop = 1;
        int countColumnBottom = 1;
        for (int i = c; i < n; i++) {
            if (i + 1 != n && cells[r][c] == cells[r][i + 1]) {
                countRowForward++;
            }
            if (i + 1 != n && cells[r][c] != cells[r][i + 1]) {
                break;
            }
            if (countRowForward == k) {
                return Result.WIN;
            }
        }
        for (int i = c; i >= 0; i--) {
            if (i - 1 != -1 && cells[r][c] == cells[r][i - 1]) {
                countRowBack++;
            }
            if (i - 1 != -1 && cells[r][i - 1] != cells[r][c]) {
                break;
            }
            if (countRowBack == k) {
                return Result.WIN;
            }
        }
        for (int i = r; i < m; i++) {
            if (i + 1 != m && cells[r][c] == cells[i + 1][c]) {
                countColumnTop++;
            }
            if (i + 1 != m && cells[r][c] != cells[i + 1][c]) {
                break;
            }
            if (countColumnTop == k) {
                return Result.WIN;
            }
        }
        for (int i = r; i >= 0; i--) {
            if (i - 1 != -1 && cells[i - 1][c] == cells[r][c]) {
                countColumnBottom++;
            }
            if (i - 1 != -1 && cells[i - 1][c] != cells[r][c]) {
                break;
            }
            if (countColumnBottom == k) {
                return Result.WIN;
            }
        }
        int countTopRight = 0;
        int countTopLeft = 0;
        int countBottomRight = 0;
        int countBottomLeft = 0;
        int const1 = r;
        int const2 = c;
        while (true) {
            if (const1 >= 0 && const2 < n && cells[r][c] == cells[const1][const2]) {
                countTopRight++;
                const2++;
                const1--;
            } else if (const1 < 0 || const2 == n || cells[r][c] != cells[const1][const2]) {
                break;
            }
        }
        if (countTopRight == k) {
            return Result.WIN;
        }
        const1 = r;
        const2 = c;
        while (true) {
            if (const1 < m && const2 >= 0 && cells[r][c] == cells[const1][const2]) {
                countBottomLeft++;
                const2--;
                const1++;
            } else if (const1 == m || const2 < 0 || cells[r][c] != cells[const1][const2] || countBottomLeft == k) {
                break;
            }
        }
        if (countBottomLeft == k) {
            return Result.WIN;
        }
        const1 = r;
        const2 = c;
        while (true) {
            if (const1 < m && const2 < n && cells[r][c] == cells[const1][const2]) {
                countBottomRight++;
                const2++;
                const1++;
            } else if (const1 == m || const2 == n || countBottomRight == k || cells[r][c] != cells[const1][const2]) {
                break;
            }
        }
        if (countBottomRight == k) {
            return Result.WIN;
        }
        const1 = r;
        const2 = c;
        while (true) {
            if (const1 >= 0 && const2 >= 0 && cells[r][c] == cells[const1][const2]) {
                countTopLeft++;
                const1--;
                const2--;
            } else if (const1 < 0 || const2 < 0 || countTopLeft == k || cells[r][c] != cells[const1][const2]) {
                break;
            }
        }
        if (countTopLeft == k) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public int isValid(Move move) {
        if (Integer.parseInt(move.getRow()) > m || Integer.parseInt(move.getColumn()) > n
                || Integer.parseInt(move.getColumn()) < 0 || Integer.parseInt(move.getRow()) < 0) {
            return 0;
        } else if (0 <= Integer.parseInt(move.getRow()) && Integer.parseInt(move.getRow()) < m
                && 0 <= Integer.parseInt(move.getColumn()) && Integer.parseInt(move.getColumn()) < n
                && cells[Integer.parseInt(move.getRow())][Integer.parseInt(move.getColumn())] == Cell.E
                && turn == getCell()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public Cell getCell(int r, int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();
        newString.append("   ");
        for (int i = 0; i < n; i++) {
            newString.append(i + 1);
            newString.append(" ");
        }
        StringBuilder sb = new StringBuilder(newString.toString());
        for (int r = 0; r < m; r++) {
            sb.append(System.lineSeparator());
            if (r + 1 >= 10) {
                sb.append(r + 1);
                sb.append("|");
            } else {
                sb.append(" ");
                sb.append(r + 1);
                sb.append("|");
            }
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}