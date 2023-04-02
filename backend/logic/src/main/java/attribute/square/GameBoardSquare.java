package attribute.square;

import error.LogicError;
import attribute.piece.ChessPiece;

import java.util.Optional;

public interface GameBoardSquare {
    GameBoardSquareCoordinates getCoordinates();
    void setPiece(ChessPiece piece) throws LogicError;
    Optional<ChessPiece> getPiece();
    ChessPiece removePiece() throws LogicError;
    boolean isEmpty();
}
