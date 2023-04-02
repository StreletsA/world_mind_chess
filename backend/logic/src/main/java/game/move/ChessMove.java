package game.move;

import attribute.square.GameBoardSquareCoordinates;
import attribute.piece.ChessPiece;

public interface ChessMove {
    ChessPiece getPiece();
    GameBoardSquareCoordinates getOldCoordinates();
    GameBoardSquareCoordinates getNewCoordinates();
}
