package game.move;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

public interface ChessMove {
    ChessPiece getPiece();
    GameBoardSquareCoordinates getOldCoordinates();
    GameBoardSquareCoordinates getNewCoordinates();
}
