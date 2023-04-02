package attribute.board;

public class GameBoardSize {
    public static final GameBoardSize BASIC = GameBoardSize.of(8, 8);

    private final int rowsCount;
    private final int columnsCount;

    public static GameBoardSize of(int rowsCount, int columnsCount) {
        return new GameBoardSize(rowsCount, columnsCount);
    }

    private GameBoardSize(int rowsCount, int columnsCount) {
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
