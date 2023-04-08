package com.github.streletsa.worldmindchess.app.chess;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.square.GameBoardSquareCoordinates;
import com.github.streletsa.worldmindchess.app.user.User;
import game.move.ChessMove;
import game.player.PlayerPawnPromotion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GlobalChessGame {
    void start();
    boolean isGameOver();
    boolean isGameStarted();
    boolean isGameFinished();
    boolean isDraw();
    ChessPieceColor getWinnerColor();
    List<String> getLoginsByColor(ChessPieceColor color);
    LocalDateTime getStartTime();
    LocalDateTime getFinishTime();
    void addPlayer(User user, ChessPieceColor color);
    Optional<ChessPieceColor> getUserColor(User user);
    long getMoveChoiceSeconds();
    ChessBoard getBoard();
    ChessPieceColor getMoveColor();
    List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates);
    void addNextMoveVote(ChessMoveVote vote);
    void addPawnPromotionVote(GlobalPawnPromotion pawnPromotion);
    List<ChessMoveVote> getCurrentMoveVotesByColor(ChessPieceColor color);
    List<GlobalPawnPromotion> getCurrentPawnPromotionVotesByColor(ChessPieceColor color);
    List<List<ChessMoveVote>> getMoveVotesHistoryByColor(ChessPieceColor color);
    List<List<GlobalPawnPromotion>> getPawnPromotionVoteHistoryByColor(ChessPieceColor color);
    void movePiece(ChessMove move);
    void promotePawn(GlobalPawnPromotion pawnPromotion);
    Map<ChessPieceColor, List<ChessMove>> getMovesHistory();
    List<ChessPiece> getRemovedPiecesByColor(ChessPieceColor color);
    List<PlayerPawnPromotion> getPromotedPawnsByColor(ChessPieceColor color);
}
