package game.player;

import attribute.piece.ChessPieceColor;
import game.ChessGame;
import game.move.ChessMove;

public interface Player {
    ChessPieceColor getColor();
    ChessGame getGame();
    void setGame(ChessGame game);
    void movePiece(ChessMove move);
}
