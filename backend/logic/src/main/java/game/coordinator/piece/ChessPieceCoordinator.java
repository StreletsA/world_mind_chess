package game.coordinator.piece;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.move.ChessMove;

import java.util.List;

public interface ChessPieceCoordinator {
    List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates, ChessBoard board);
    ChessPieceType getPieceType();

    default void checkChessPiece(GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        ChessPiece piece = board.getPiece(startCoordinates)
                .orElseThrow(() -> new LogicError("Chess piece not found"));

        if (piece.getType() != getPieceType()) {
            throw new LogicError("Incorrect chess type");
        }
    }
}
