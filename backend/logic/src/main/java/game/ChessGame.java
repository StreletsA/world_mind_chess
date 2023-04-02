package game;

import attribute.board.GameBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import game.player.Player;
import game.move.PlayerChessMove;
import game.player.PlayerPawnPromotion;

import java.util.List;

public interface ChessGame {
    Player getPlayerWhite();
    Player getPlayerBlack();
    GameBoard getBoard();
    ChessPieceColor getMoveColor();
    void movePiece(PlayerChessMove move);
    void promotePawn(PlayerPawnPromotion pawnPromotion);
    List<PlayerChessMove> getMovesHistory();

    List<ChessPiece> getRemovedWhitePieces();

    List<ChessPiece> getRemovedBlackPieces();

    List<PlayerPawnPromotion> getPromotedWhitePawns();

    List<PlayerPawnPromotion> getPromotedBlackPawns();
}
