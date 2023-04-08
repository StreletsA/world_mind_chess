package game.coordinator.piece;

import attribute.board.ChessBoard;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import game.move.BasicChessMove;
import game.move.ChessMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KingPieceCoordinator extends BasicChessPieceCoordinator {
    public KingPieceCoordinator(Map<ChessPieceType, BasicChessPieceCoordinator> coordinatorMap) {
        super(coordinatorMap);
    }

    @Override
    public List<ChessMove> getMoves(ChessPiece piece, GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        ChessPieceColor color = piece.getColor();
        int row = startCoordinates.getRow();
        int column = startCoordinates.getColumn();

        List<ChessMove> moves = new ArrayList<>();

        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int columnDelta = -1; columnDelta <= 1; columnDelta++) {
                if (rowDelta == 0 && columnDelta == 0) {
                    continue;
                }

                GameBoardSquareCoordinates newCoordinates = GameBoardSquareCoordinates.of(row + rowDelta, column + columnDelta);

                if (!isCoordinatesInBoardSizeRange(newCoordinates, board)) {
                    continue;
                }

                ChessPiece newCoordinatesPiece = board.getPiece(newCoordinates).orElse(null);

                if (newCoordinatesPiece != null && newCoordinatesPiece.getColor() != color) {
                    moves.add(BasicChessMove.of(piece, startCoordinates, newCoordinates));
                }
            }
        }

        return moves;
    }

    @Override
    public ChessPieceType getPieceType() {
        return ChessPieceType.KING;
    }
}
