package attribute.row;

import attribute.square.GameBoardSquare;

import java.util.List;

public interface GameBoardRow {
    int getRowIndex();
    int columnsCount();
    List<GameBoardSquare> getSquares();
    GameBoardSquare getSquare(int columnIndex);
}
