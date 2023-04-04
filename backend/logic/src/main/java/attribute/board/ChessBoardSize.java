package attribute.board;

public class ChessBoardSize {
    public static final ChessBoardSize BASIC = ChessBoardSize.of(8, 8);

    private final int rowsCount;
    private final int columnsCount;

    public static ChessBoardSize of(int rowsCount, int columnsCount) {
        return new ChessBoardSize(rowsCount, columnsCount);
    }

    private ChessBoardSize(int rowsCount, int columnsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }
}
