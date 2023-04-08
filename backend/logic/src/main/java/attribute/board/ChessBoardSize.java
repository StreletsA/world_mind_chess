package attribute.board;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ChessBoardSize)) {
            return false;
        }

        ChessBoardSize other = (ChessBoardSize) obj;

        return Objects.equals(rowsCount, other.rowsCount)
                && Objects.equals(columnsCount, other.columnsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowsCount, columnsCount);
    }
}
