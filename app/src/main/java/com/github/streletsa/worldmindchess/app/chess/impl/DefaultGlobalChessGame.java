package com.github.streletsa.worldmindchess.app.chess.impl;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.square.GameBoardSquareCoordinates;
import com.github.streletsa.worldmindchess.app.chess.*;
import com.github.streletsa.worldmindchess.app.error.AppError;
import com.github.streletsa.worldmindchess.app.user.User;
import game.ChessGame;
import game.move.BasicPlayerChessMove;
import game.move.ChessMove;
import game.player.BasicPlayerPawnPromotion;
import game.player.Player;
import game.player.PlayerPawnPromotion;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultGlobalChessGame implements GlobalChessGame {
    private final ChessGame game;
    private final Map<String, ChessPieceColor> userTeams = new HashMap<>();
    private final Map<ChessPieceColor, List<ChessMove>> movesHistory = new HashMap<>();
    private final Map<ChessPieceColor, List<GlobalPawnPromotion>> promotedPawnsHistory = new HashMap<>();
    private final List<Map<ChessPieceColor, List<ChessMoveVote>>> moveVotesHistory = new ArrayList<>();
    private final List<Map<ChessPieceColor, List<GlobalPawnPromotion>>> promotePawnVotesHistory = new ArrayList<>();

    private Map<ChessPieceColor, List<ChessMoveVote>> moveVotes = new HashMap<>();
    private Map<ChessPieceColor, List<GlobalPawnPromotion>> promotedPawnVotes = new HashMap<>();
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private boolean gameStarted = false;
    private boolean gameFinished = false;
    private long moveChoiceSeconds = 300;

    public DefaultGlobalChessGame(ChessGame game, long moveChoiceSeconds) {
        this(game);

        this.moveChoiceSeconds = moveChoiceSeconds;
    }

    public DefaultGlobalChessGame(ChessGame game) {
        this.game = game;

        moveVotes.put(ChessPieceColor.WHITE, new ArrayList<>());
        moveVotes.put(ChessPieceColor.BLACK, new ArrayList<>());
        movesHistory.put(ChessPieceColor.WHITE, new ArrayList<>());
        movesHistory.put(ChessPieceColor.BLACK, new ArrayList<>());
        promotedPawnVotes.put(ChessPieceColor.WHITE, new ArrayList<>());
        promotedPawnVotes.put(ChessPieceColor.BLACK, new ArrayList<>());
        promotedPawnsHistory.put(ChessPieceColor.WHITE, new ArrayList<>());
        promotedPawnsHistory.put(ChessPieceColor.BLACK, new ArrayList<>());
    }

    @Override
    public void start() {
        if (gameStarted) {
            throw new AppError("Game already started");
        }

        startTime = LocalDateTime.now();
        gameStarted = true;

        MoveDeterminant moveDeterminant = new MoveDeterminant(this);
        moveDeterminant.start();
    }

    @Override
    public boolean isGameOver() {
        return game.getStatus().isGameOver();
    }

    @Override
    public boolean isGameFinished() {
        return gameFinished;
    }

    @Override
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    @Override
    public boolean isGameStarted() {
        return gameStarted;
    }

    @Override
    public boolean isDraw() {
        return game.getStatus().isDraw();
    }

    @Override
    public ChessPieceColor getWinnerColor() {
        if (!game.getStatus().isGameOver()) {
            throw new AppError("Game is not over");
        }

        Player winner = game.getStatus().getWinner();

        if (winner != null) {
            return winner.getColor();
        }

        return null;
    }

    @Override
    public List<String> getLoginsByColor(ChessPieceColor color) {
        return userTeams.keySet().stream().filter(login -> userTeams.get(login) == color).collect(Collectors.toList());
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void addPlayer(User user, ChessPieceColor color) {
        userTeams.put(user.getLogin(), color);
    }

    @Override
    public Optional<ChessPieceColor> getUserColor(User user) {
        String login = user.getLogin();

        return Optional.ofNullable(userTeams.get(login));
    }

    @Override
    public long getMoveChoiceSeconds() {
        return moveChoiceSeconds;
    }

    @Override
    public ChessBoard getBoard() {
        return game.getBoard();
    }

    @Override
    public ChessPieceColor getMoveColor() {
        return game.getMoveColor();
    }

    @Override
    public List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates) {
        ChessPiece piece = game.getBoard().getPiece(startCoordinates).orElseThrow(() -> new AppError("Chess piece not found"));
        ChessPieceColor color = piece.getColor();
        
        return color == ChessPieceColor.WHITE 
                ? game.getAllowedMoves(game.getPlayerWhite())
                : game.getAllowedMoves(game.getPlayerBlack());
    }

    @Override
    public void addNextMoveVote(ChessMoveVote vote) {
        moveVotes.get(vote.getMove().getPiece().getColor()).add(vote);
    }

    @Override
    public void addPawnPromotionVote(GlobalPawnPromotion pawnPromotion) {
        promotedPawnVotes.get(pawnPromotion.getPiece().getColor()).add(pawnPromotion);
    }

    @Override
    public List<ChessMoveVote> getCurrentMoveVotesByColor(ChessPieceColor color) {
        return moveVotes.get(color);
    }

    @Override
    public List<GlobalPawnPromotion> getCurrentPawnPromotionVotesByColor(ChessPieceColor color) {
        return promotedPawnVotes.get(color);
    }

    @Override
    public List<List<ChessMoveVote>> getMoveVotesHistoryByColor(ChessPieceColor color) {
        return moveVotesHistory.stream().map(m -> m.get(color)).collect(Collectors.toList());
    }

    @Override
    public List<List<GlobalPawnPromotion>> getPawnPromotionVoteHistoryByColor(ChessPieceColor color) {
        return promotePawnVotesHistory.stream().map(m -> m.get(color)).collect(Collectors.toList());
    }

    @Override
    public void movePiece(ChessMove move) {
        if (gameFinished) {
            throw new AppError("Game already finished");
        }

        ChessPieceColor color = move.getPiece().getColor();
        Player player = color == ChessPieceColor.WHITE ? game.getPlayerWhite() : game.getPlayerBlack();
        BasicPlayerChessMove chessMove = BasicPlayerChessMove.of(player, move);

        game.movePiece(chessMove);

        moveVotesHistory.add(moveVotes);
        movesHistory.get(color).add(move);

        moveVotes = new HashMap<>();
        moveVotes.put(ChessPieceColor.WHITE, new ArrayList<>());
        moveVotes.put(ChessPieceColor.BLACK, new ArrayList<>());

        ChessPieceColor moveColor = game.getMoveColor();
        player = moveColor == ChessPieceColor.WHITE ? game.getPlayerWhite() : game.getPlayerBlack();
        List<ChessMove> allowedMoves = game.getAllowedMoves(player);

        if (allowedMoves.isEmpty()) {
            finishTime = LocalDateTime.now();
            gameFinished = true;
            game.setWinnerColor(color);
        }
    }

    @Override
    public void promotePawn(GlobalPawnPromotion pawnPromotion) {
        if (gameFinished) {
            throw new AppError("Game already finished");
        }

        ChessPiece piece = pawnPromotion.getPiece();
        GameBoardSquareCoordinates coordinates = pawnPromotion.getCoordinates();
        ChessPieceColor color = piece.getColor();
        Player player = color == ChessPieceColor.WHITE
                ? game.getPlayerWhite()
                : game.getPlayerBlack();

        game.promotePawn(BasicPlayerPawnPromotion.of(player, coordinates, piece));

        promotedPawnsHistory.get(color).add(pawnPromotion);
        promotedPawnVotes = new HashMap<>();
        promotedPawnVotes.put(ChessPieceColor.WHITE, new ArrayList<>());
        promotedPawnVotes.put(ChessPieceColor.BLACK, new ArrayList<>());

        ChessPieceColor moveColor = game.getMoveColor();
        player = moveColor == ChessPieceColor.WHITE ? game.getPlayerWhite() : game.getPlayerBlack();
        List<ChessMove> allowedMoves = game.getAllowedMoves(player);

        if (allowedMoves.isEmpty()) {
            finishTime = LocalDateTime.now();
            gameFinished = true;
            game.setWinnerColor(color);
        }
    }

    @Override
    public Map<ChessPieceColor, List<ChessMove>> getMovesHistory() {
        return movesHistory;
    }

    @Override
    public List<ChessPiece> getRemovedPiecesByColor(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE
                ? game.getRemovedWhitePieces()
                : game.getRemovedBlackPieces();
    }

    @Override
    public List<PlayerPawnPromotion> getPromotedPawnsByColor(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE
                ? game.getPromotedWhitePawns()
                : game.getPromotedBlackPawns();
    }

    // TODO: 08.04.2023 Create Executor
    private static final class MoveDeterminant extends Thread {
        private final DefaultGlobalChessGame chessGame;

        public MoveDeterminant(DefaultGlobalChessGame chessGame) {
            this.chessGame = chessGame;
        }

        @Override
        public void run() {
            while (!chessGame.isGameFinished()) {
                try {
                    ChessPieceColor moveColor = chessGame.getMoveColor();
                    List<ChessMoveVote> chessMoveVotes = chessGame.getCurrentMoveVotesByColor(moveColor);
                    List<GlobalPawnPromotion> pawnPromotionVotes = chessGame.getCurrentPawnPromotionVotesByColor(moveColor);

                    Map<ChessMove, Long> moves = chessMoveVotes.stream()
                            .collect(Collectors.groupingBy(ChessMoveVote::getMove, Collectors.counting()));
                    Map<GlobalPawnPromotion, Long> pawnPromotions = pawnPromotionVotes.stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                    if (moves.keySet().isEmpty() && pawnPromotions.keySet().isEmpty()) {
                        Player player = moveColor == ChessPieceColor.WHITE
                                ? chessGame.game.getPlayerWhite()
                                : chessGame.game.getPlayerBlack();

                        List<ChessMove> allowedMoves = chessGame.game.getAllowedMoves(player);

                        if (!allowedMoves.isEmpty()) {
                            chessGame.movePiece(allowedMoves.get(0));
                        } else {
                            chessGame.game.setWinnerColor(moveColor == ChessPieceColor.WHITE
                                    ? ChessPieceColor.BLACK
                                    : ChessPieceColor.WHITE);
                            chessGame.gameFinished = true;
                            chessGame.finishTime = LocalDateTime.now();
                        }
                    } else {
                        long maxMoveVotes = 0;
                        ChessMove move = null;
                        long maxPawnPromotionVotes = 0;
                        GlobalPawnPromotion pawnPromotion = null;

                        for (Map.Entry<ChessMove, Long> entry : moves.entrySet()) {
                            ChessMove chessMove = entry.getKey();
                            Long votesCount = entry.getValue();

                            if (votesCount > maxMoveVotes) {
                                maxMoveVotes = votesCount;
                                move = chessMove;
                            }
                        }

                        for (Map.Entry<GlobalPawnPromotion, Long> entry : pawnPromotions.entrySet()) {
                            GlobalPawnPromotion promotion = entry.getKey();
                            Long votesCount = entry.getValue();

                            if (votesCount > maxPawnPromotionVotes) {
                                maxPawnPromotionVotes = votesCount;
                                pawnPromotion = promotion;
                            }
                        }

                        if (maxMoveVotes > maxPawnPromotionVotes) {
                            chessGame.movePiece(move);
                        } else {
                            chessGame.promotePawn(pawnPromotion);
                        }
                    }

                    Thread.sleep(chessGame.getMoveChoiceSeconds() * 1000);
                } catch (InterruptedException e) {
                    chessGame.game.draw();
                    chessGame.gameFinished = true;
                    chessGame.finishTime = LocalDateTime.now();

                    throw new AppError("MoveDeterminant broke");
                }
            }
        }
    }
}
