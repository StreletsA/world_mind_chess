package game.coordinator;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.coordinator.piece.BasicChessPieceCoordinator;
import game.move.ChessMove;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BasicChessGameCoordinator implements ChessGameCoordinator {
    private final Map<ChessPieceType, BasicChessPieceCoordinator> chessPieceCoordinators;

    public BasicChessGameCoordinator(Map<ChessPieceType, BasicChessPieceCoordinator> chessPieceCoordinators) {
        this.chessPieceCoordinators = chessPieceCoordinators;
    }

    @Override
    public List<ChessMove> getAllowedMoves(Player player, ChessBoard board) {
        List<ChessMove> allowedMoves = new ArrayList<>();
        ChessPieceColor playerColor = player.getColor();
        Map<GameBoardSquareCoordinates, ChessPiece> pieces = board.getPieces();

        for (Map.Entry<GameBoardSquareCoordinates, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();

            if (!Objects.equals(piece.getColor(), playerColor)) {
                continue;
            }

            ChessPieceType pieceType = piece.getType();
            GameBoardSquareCoordinates coordinates = entry.getKey();
            List<ChessMove> allowedMovesForCurrentPiece =
                    chessPieceCoordinators.get(pieceType).getAllowedMoves(coordinates, board);

            allowedMoves.addAll(allowedMovesForCurrentPiece);
        }

        return allowedMoves;
    }

    @Override
    public List<ChessMove> getAllowedMoves(Player player, GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        ChessPiece piece = board.getPiece(startCoordinates).orElseThrow(() -> new LogicError("Chess piece not found"));
        ChessPieceType pieceType = piece.getType();

        return chessPieceCoordinators.get(pieceType).getAllowedMoves(startCoordinates, board);
    }

    @Override
    public List<ChessMove> getChecksForPlayer(Player player, ChessBoard board) {
        List<ChessMove> checkMoves = new ArrayList<>();

        ChessPieceColor playerColor = player.getColor();
        GameBoardSquareCoordinates currentKingCoordinates = getCurrentKingCoordinates(playerColor, board);
        ChessPieceColor opponentColor = playerColor == ChessPieceColor.WHITE ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
        Map<GameBoardSquareCoordinates, ChessPiece> pieces = board.getPieces();

        for (Map.Entry<GameBoardSquareCoordinates, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();

            if (!Objects.equals(piece.getColor(), opponentColor)) {
                continue;
            }

            ChessPieceType pieceType = piece.getType();
            GameBoardSquareCoordinates coordinates = entry.getKey();
            List<ChessMove> opponentMoves =
                    chessPieceCoordinators.get(pieceType).getMoves(piece, coordinates, board);
            opponentMoves = opponentMoves.stream()
                    .filter(chessMove -> Objects.equals(chessMove.getNewCoordinates(), currentKingCoordinates))
                    .collect(Collectors.toList());

            checkMoves.addAll(opponentMoves);
        }

        return checkMoves;
    }

    private GameBoardSquareCoordinates getCurrentKingCoordinates(ChessPieceColor currentColor, ChessBoard board) {
        Map<GameBoardSquareCoordinates, ChessPiece> pieces = board.getPieces();

        for (Map.Entry<GameBoardSquareCoordinates, ChessPiece> entry : pieces.entrySet()) {
            GameBoardSquareCoordinates coordinates = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor() == currentColor && piece.getType() == ChessPieceType.KING) {
                return coordinates;
            }
        }

        return null;
    }
}
