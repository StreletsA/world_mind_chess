package game.coordinator;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import game.coordinator.piece.ChessPieceCoordinator;
import game.coordinator.piece.PawnCoordinator;
import game.move.ChessMove;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BasicChessGameCoordinator implements ChessGameCoordinator {
    private static final Map<ChessPieceType, ChessPieceCoordinator> CHESS_PIECE_COORDINATORS = Map.of(
            ChessPieceType.PAWN, new PawnCoordinator()
    );

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
                    CHESS_PIECE_COORDINATORS.get(pieceType).getAllowedMoves(coordinates, board);

            allowedMoves.addAll(allowedMovesForCurrentPiece);
        }

        return allowedMoves;
    }

    @Override
    public List<ChessMove> getChecksForPlayer(Player player, ChessBoard board) {
        return null;
    }
}
