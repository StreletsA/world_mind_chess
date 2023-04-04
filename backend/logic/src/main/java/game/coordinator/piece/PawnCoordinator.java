package game.coordinator.piece;

import attribute.board.ChessBoard;
import attribute.board.ChessBoardSize;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import game.move.BasicChessMove;
import game.move.ChessMove;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PawnCoordinator implements ChessPieceCoordinator {
    @Override
    public List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        checkChessPiece(startCoordinates, board);

        ChessPiece piece = board.getPiece(startCoordinates).orElse(null);

        assert piece != null;

        ChessPieceColor color = piece.getColor();
        boolean isWhite = color == ChessPieceColor.WHITE;
        int row = startCoordinates.getRow();
        int column = startCoordinates.getColumn();
        int rowDelta = isWhite ? 1 : -1;

        List<ChessMove> moves = new ArrayList<>();
        moves.add(BasicChessMove.of(piece, startCoordinates, GameBoardSquareCoordinates.of(row + rowDelta, column)));
        moves.add(BasicChessMove.of(piece, startCoordinates, GameBoardSquareCoordinates.of(row + rowDelta, column + 1)));
        moves.add(BasicChessMove.of(piece, startCoordinates, GameBoardSquareCoordinates.of(row + rowDelta, column - 1)));

        if (isWhite && row == 1 || !isWhite && row == 6) {
            moves.add(BasicChessMove.of(piece, startCoordinates, GameBoardSquareCoordinates.of(row + (2 * rowDelta), column)));
        }

        correctMoves(moves, color, board);

        return moves;
    }

    private void correctMoves(List<ChessMove> moves, ChessPieceColor currentColor, ChessBoard board) {
        ChessBoardSize size = board.getSize();

        Iterator<ChessMove> iterator = moves.iterator();

        while (iterator.hasNext()) {
            ChessMove move = iterator.next();
            GameBoardSquareCoordinates newCoordinates = move.getNewCoordinates();
            int row = newCoordinates.getRow();
            int column = newCoordinates.getColumn();

            if (row >= 0 && row < size.getRowsCount() && column >= 0 && column < size.getColumnsCount()) {
                ChessPiece piece = board.getPiece(newCoordinates).orElse(null);

                if (piece != null && piece.getColor() == currentColor) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
            }
        }
    }

    @Override
    public ChessPieceType getPieceType() {
        return ChessPieceType.PAWN;
    }
}
