package attribute.row;

import attribute.square.GameBoardSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicGameBoardRow implements GameBoardRow {
    private final int rowIndex;
    private final List<GameBoardSquare> squares;

    public static BasicGameBoardRow of(int rowIndex, List<GameBoardSquare> squares) {
        return new BasicGameBoardRow(rowIndex, squares);
    }

    private BasicGameBoardRow(int rowIndex, List<GameBoardSquare> squares) {
        this.rowIndex = rowIndex;
        this.squares = new ArrayList<>(squares);
    }

    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public int columnsCount() {
        return squares.size();
    }

    @Override
    public List<GameBoardSquare> getSquares() {
        return squares;
    }

    @Override
    public GameBoardSquare getSquare(int columnIndex) {
        return squares.get(columnIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BasicGameBoardRow)) {
            return false;
        }

        BasicGameBoardRow other = (BasicGameBoardRow) obj;

        return Objects.equals(rowIndex, other.rowIndex)
                && Objects.equals(squares, other.squares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, squares);
    }
}
