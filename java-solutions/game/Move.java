package game;

public final class Move {
    private final String row;
    private final String column;
    private final Cell value;

    public Move(final String row, final String column, final Cell value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public String getRow() {
        try {
            return row;
        } catch (NumberFormatException e) {
            System.out.println("You write something dumb:(");
        }
        return row;
    }

    public String getColumn() {
        try {
            return column;
        } catch (NumberFormatException e) {
            System.out.println("exception");
        }
        return column;
    }

    public Cell getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "row=" + (Integer.parseInt(row) + 1) + ", column=" + (Integer.parseInt(column) + 1) + ", value=" + value;
    }
}
