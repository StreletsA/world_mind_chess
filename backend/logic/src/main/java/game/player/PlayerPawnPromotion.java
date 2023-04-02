package game.player;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;
import game.player.Player;

public interface PlayerPawnPromotion {
    Player getPlayer();
    GameBoardSquareCoordinates getCoordinates();
    ChessPiece getPiece();
}
