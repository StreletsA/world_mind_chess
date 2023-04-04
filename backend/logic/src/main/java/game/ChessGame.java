package game;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import game.player.Player;
import game.move.PlayerChessMove;
import game.player.PlayerPawnPromotion;
import game.status.GameStatus;

import java.util.List;

public interface ChessGame {
    Player getPlayerWhite();
    Player getPlayerBlack();
    ChessBoard getBoard();
    ChessPieceColor getMoveColor();
    GameStatus getStatus();
    void movePiece(PlayerChessMove move);
    void promotePawn(PlayerPawnPromotion pawnPromotion);
    List<PlayerChessMove> getMovesHistory();
    List<ChessPiece> getRemovedWhitePieces();
    List<ChessPiece> getRemovedBlackPieces();
    List<PlayerPawnPromotion> getPromotedWhitePawns();
    List<PlayerPawnPromotion> getPromotedBlackPawns();
}
