package game.player;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

public interface PlayerPawnPromotion {
    Player getPlayer();
    GameBoardSquareCoordinates getCoordinates();
    ChessPiece getPiece();
}
