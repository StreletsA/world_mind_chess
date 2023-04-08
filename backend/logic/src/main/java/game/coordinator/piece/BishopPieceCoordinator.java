package game.coordinator.piece;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import game.move.ChessMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BishopPieceCoordinator extends BasicChessPieceCoordinator {
    public BishopPieceCoordinator(Map<ChessPieceType, BasicChessPieceCoordinator> coordinatorMap) {
        super(coordinatorMap);
    }

    @Override
    public List<ChessMove> getMoves(ChessPiece piece, GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        List<ChessMove> moves = new ArrayList<>();
        moves.addAll(getMoves(piece, startCoordinates, startCoordinates, 1, 1, board));
        moves.addAll(getMoves(piece, startCoordinates, startCoordinates, 1, -1, board));
        moves.addAll(getMoves(piece, startCoordinates, startCoordinates, -1, 1, board));
        moves.addAll(getMoves(piece, startCoordinates, startCoordinates, -1, -1, board));

        return moves;
    }

    @Override
    public ChessPieceType getPieceType() {
        return ChessPieceType.BISHOP;
    }
}
