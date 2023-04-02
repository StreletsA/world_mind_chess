package attribute.board;

import attribute.row.GameBoardRow;
import attribute.square.GameBoardSquare;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.move.ChessMove;
import attribute.piece.ChessPiece;

import java.util.List;
import java.util.Optional;

public interface GameBoard {
    GameBoardSize getSize();
    void clear();
    List<GameBoardRow> getRows();
    GameBoardRow getRow(int rowIndex) throws LogicError;
    GameBoardSquare getSquare(GameBoardSquareCoordinates coordinates) throws LogicError;
    void setPiece(ChessPiece piece, GameBoardSquareCoordinates coordinates) throws LogicError;
    Optional<ChessPiece> getPiece(GameBoardSquareCoordinates coordinates);
    void removePiece(GameBoardSquareCoordinates coordinates) throws LogicError;
    Optional<ChessPiece> movePiece(ChessMove move);
}
