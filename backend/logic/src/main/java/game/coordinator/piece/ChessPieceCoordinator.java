package game.coordinator.piece;

import attribute.board.ChessBoard;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import game.move.ChessMove;

import java.util.List;

public interface ChessPieceCoordinator {
    List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates, ChessBoard board);
    ChessPieceType getPieceType();
}
