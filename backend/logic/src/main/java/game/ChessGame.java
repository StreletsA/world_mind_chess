package game;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.square.GameBoardSquareCoordinates;
import game.move.ChessMove;
import game.move.PlayerChessMove;
import game.player.Player;
import game.player.PlayerPawnPromotion;
import game.status.GameStatus;

import java.util.List;

public interface ChessGame {
    Player getPlayerWhite();
    Player getPlayerBlack();
    void setPlayerWhite(Player playerWhite);
    void setPlayerBlack(Player playerBlack);
    void draw();
    ChessBoard getBoard();
    ChessPieceColor getMoveColor();
    GameStatus getStatus();
    List<ChessMove> getAllowedMoves(Player player, GameBoardSquareCoordinates startCoordinates);
    List<ChessMove> getAllowedMoves(Player player);
    void movePiece(PlayerChessMove move);
    void promotePawn(PlayerPawnPromotion pawnPromotion);
    List<PlayerChessMove> getMovesHistory();
    List<ChessPiece> getRemovedWhitePieces();
    List<ChessPiece> getRemovedBlackPieces();
    List<PlayerPawnPromotion> getPromotedWhitePawns();
    List<PlayerPawnPromotion> getPromotedBlackPawns();
}
