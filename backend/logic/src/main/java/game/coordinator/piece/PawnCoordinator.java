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

public class PawnCoordinator extends BasicChessPieceCoordinator {
    public PawnCoordinator(Map<ChessPieceType, BasicChessPieceCoordinator> coordinatorMap) {
        super(coordinatorMap);
    }

    @Override
    public List<ChessMove> getMoves(ChessPiece piece, GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        ChessPieceColor color = piece.getColor();
        boolean isWhite = color == ChessPieceColor.WHITE;
        int row = startCoordinates.getRow();
        int column = startCoordinates.getColumn();
        int rowDelta = isWhite ? 1 : -1;

        List<ChessMove> moves = new ArrayList<>();

        for (int columnDelta = -1; columnDelta <= 1; columnDelta++) {
            GameBoardSquareCoordinates newCoordinates = GameBoardSquareCoordinates.of(row + rowDelta, column + columnDelta);

            if (!isCoordinatesInBoardSizeRange(newCoordinates, board)) {
                continue;
            }

            ChessPiece newCoordinatesPiece = board.getPiece(newCoordinates).orElse(null);

            if (newCoordinatesPiece != null && newCoordinatesPiece.getColor() != color) {
                moves.add(BasicChessMove.of(piece, startCoordinates, newCoordinates));
            }
        }

        if (isWhite && row == 1 || !isWhite && row == 6) {
            GameBoardSquareCoordinates newCoordinates = GameBoardSquareCoordinates.of(row + (2 * rowDelta), column);

            if (isCoordinatesInBoardSizeRange(newCoordinates, board)) {
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
        return ChessPieceType.PAWN;
    }
}
