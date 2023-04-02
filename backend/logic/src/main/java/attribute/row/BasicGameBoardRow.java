package attribute.row;

import attribute.square.GameBoardSquare;

import java.util.ArrayList;
import java.util.List;

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
}
