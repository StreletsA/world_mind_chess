package game;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.player.Player;
import game.move.PlayerChessMove;
import game.player.PlayerPawnPromotion;
import game.status.BasicGameStatus;
import game.status.GameStatus;

import java.util.*;

public class BasicChessGame implements ChessGame {
    private static final List<ChessPieceCoordinates> BASIC_PIECE_COORDINATES = new ArrayList<>();

    static {
        Map<Integer, ChessPieceType> columnPieceTypeMap = Map.of(
                0, ChessPieceType.ROOK,
                1, ChessPieceType.KNIGHT,
                2, ChessPieceType.BISHOP,
                3, ChessPieceType.QUEEN,
                4, ChessPieceType.KING,
                5, ChessPieceType.BISHOP,
                6, ChessPieceType.KNIGHT,
                7, ChessPieceType.ROOK
        );

        for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
            BASIC_PIECE_COORDINATES.add(ChessPieceCoordinates.of(
                            ChessPiece.of(ChessPieceType.PAWN, ChessPieceColor.WHITE),
                            GameBoardSquareCoordinates.of(1, columnIndex)
                    )
            );
            BASIC_PIECE_COORDINATES.add(ChessPieceCoordinates.of(
                            ChessPiece.of(ChessPieceType.PAWN, ChessPieceColor.BLACK),
                            GameBoardSquareCoordinates.of(6, columnIndex)
                    )
            );

            ChessPieceType chessPieceType = columnPieceTypeMap.get(columnIndex);

            BASIC_PIECE_COORDINATES.add(ChessPieceCoordinates.of(
                            ChessPiece.of(chessPieceType, ChessPieceColor.WHITE),
                            GameBoardSquareCoordinates.of(0, columnIndex)
                    )
            );
            BASIC_PIECE_COORDINATES.add(ChessPieceCoordinates.of(
                            ChessPiece.of(chessPieceType, ChessPieceColor.BLACK),
                            GameBoardSquareCoordinates.of(7, columnIndex)
                    )
            );
        }
    }

    private Player playerWhite;
    private Player playerBlack;
    private final ChessBoard board;
    private final GameStatus status;
    private final List<PlayerChessMove> movesHistory = new ArrayList<>();
    private final List<ChessPiece> removedWhitePieces = new ArrayList<>();
    private final List<ChessPiece> removedBlackPieces = new ArrayList<>();
    private final List<PlayerPawnPromotion> promotedWhitePawns = new ArrayList<>();
    private final List<PlayerPawnPromotion> promotedBlackPawns = new ArrayList<>();

    private ChessPieceColor moveColor = ChessPieceColor.WHITE;

    public static BasicChessGame of(ChessBoard board) {
        return new BasicChessGame(null, null, board);
    }

    public static BasicChessGame of(Player playerWhite, Player playerBlack, ChessBoard board) {
        return new BasicChessGame(playerWhite, playerBlack, board);
    }

    private BasicChessGame(Player playerWhite, Player playerBlack, ChessBoard board) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        this.board = board;
        this.status = BasicGameStatus.START_GAME_STATUS;

        initBoard();
    }

    private void initBoard() {
        board.clear();

        for (ChessPieceCoordinates pieceCoordinate : BASIC_PIECE_COORDINATES) {
            ChessPiece piece = pieceCoordinate.getPiece();
            GameBoardSquareCoordinates coordinates = pieceCoordinate.getCoordinates();

            board.setPiece(piece, coordinates);
        }
    }

    void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    @Override
    public Player getPlayerWhite() {
        return playerWhite;
    }

    @Override
    public Player getPlayerBlack() {
        return playerBlack;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public ChessPieceColor getMoveColor() {
        return moveColor;
    }

    @Override
    public List<PlayerChessMove> getMovesHistory() {
        return movesHistory;
    }

    @Override
    public List<ChessPiece> getRemovedWhitePieces() {
        return removedWhitePieces;
    }

    @Override
    public List<ChessPiece> getRemovedBlackPieces() {
        return removedBlackPieces;
    }

    @Override
    public List<PlayerPawnPromotion> getPromotedWhitePawns() {
        return promotedWhitePawns;
    }

    @Override
    public List<PlayerPawnPromotion> getPromotedBlackPawns() {
        return promotedBlackPawns;
    }

    @Override
    public void movePiece(PlayerChessMove move) {
        Player playerOfMove = move.getPlayer();
        ChessPieceColor color = playerOfMove.getColor();

        if (color != moveColor) {
            throw new LogicError("Now it's the other player's turn");
        }

        Optional<ChessPiece> removedPiece = board.movePiece(move.getMove());

        if (removedPiece.isPresent()) {
            ChessPiece chessPiece = removedPiece.get();

            if (Objects.equals(playerWhite, playerOfMove)) {
                removedBlackPieces.add(chessPiece);
            } else {
                removedWhitePieces.add(chessPiece);
            }
        }

        moveColor = color == ChessPieceColor.WHITE ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;

        movesHistory.add(move);
    }

    @Override
    public void promotePawn(PlayerPawnPromotion pawnPromotion) {
        GameBoardSquareCoordinates coordinates = pawnPromotion.getCoordinates();
        ChessPiece piece = pawnPromotion.getPiece();
        Player player = pawnPromotion.getPlayer();

        board.removePiece(coordinates);
        board.setPiece(piece, coordinates);

        if (Objects.equals(playerWhite, player)) {
            promotedWhitePawns.add(pawnPromotion);
        } else {
            promotedBlackPawns.add(pawnPromotion);
        }
    }

    private static final class ChessPieceCoordinates {
        private final ChessPiece piece;
        private final GameBoardSquareCoordinates coordinates;

        public static ChessPieceCoordinates of(ChessPiece piece, GameBoardSquareCoordinates coordinates) {
            return new ChessPieceCoordinates(piece, coordinates);
        }

        private ChessPieceCoordinates(ChessPiece piece, GameBoardSquareCoordinates coordinates) {
            this.piece = piece;
            this.coordinates = coordinates;
        }

        public ChessPiece getPiece() {
            return piece;
        }

        public GameBoardSquareCoordinates getCoordinates() {
            return coordinates;
        }
    }
}
