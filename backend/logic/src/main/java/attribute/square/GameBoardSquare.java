package attribute.square;

import attribute.piece.ChessPiece;
import error.LogicError;

import java.util.Optional;

public interface GameBoardSquare {
    GameBoardSquareCoordinates getCoordinates();
    void setPiece(ChessPiece piece) throws LogicError;
    Optional<ChessPiece> getPiece();
    ChessPiece removePiece() throws LogicError;
    boolean isEmpty();
}
