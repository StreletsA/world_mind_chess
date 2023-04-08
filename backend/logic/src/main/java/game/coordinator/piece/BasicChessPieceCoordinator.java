package game.coordinator.piece;

import attribute.board.BasicChessBoard;
import attribute.board.ChessBoard;
import attribute.board.ChessBoardSize;
import attribute.piece.ChessPiece;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.move.BasicChessMove;
import game.move.ChessMove;

import java.util.*;

public abstract class BasicChessPieceCoordinator implements ChessPieceCoordinator {
    private final Map<ChessPieceType, BasicChessPieceCoordinator> coordinatorMap;

    public BasicChessPieceCoordinator(Map<ChessPieceType, BasicChessPieceCoordinator> coordinatorMap) {
        this.coordinatorMap = coordinatorMap;
    }

    @Override
    public List<ChessMove> getAllowedMoves(GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        checkChessPiece(startCoordinates, board);

        ChessPiece piece = board.getPiece(startCoordinates).orElse(null);

        assert piece != null;

        List<ChessMove> moves = getMoves(piece, startCoordinates, board);
        correctMovesByCheck(moves, piece.getColor(), board);

        return moves;
    }

    public abstract List<ChessMove> getMoves(ChessPiece piece, GameBoardSquareCoordinates startCoordinates, ChessBoard board);

    @Override
    public abstract ChessPieceType getPieceType();

    private void checkChessPiece(GameBoardSquareCoordinates startCoordinates, ChessBoard board) {
        ChessPiece piece = board.getPiece(startCoordinates)
                .orElseThrow(() -> new LogicError("Chess piece not found"));

        if (piece.getType() != getPieceType()) {
            throw new LogicError("Incorrect chess type");
        }
    }

    private void correctMovesByCheck(List<ChessMove> moves, ChessPieceColor currentColor, ChessBoard board) {
        GameBoardSquareCoordinates currentKingCoordinates = getCurrentKingCoordinates(currentColor, board);

        ChessBoard tmpBoard = null;

        Iterator<ChessMove> iterator = moves.iterator();

        while (iterator.hasNext()) {
            ChessMove move = iterator.next();
            GameBoardSquareCoordinates newCoordinates = move.getNewCoordinates();

            ChessPiece piece = board.getPiece(newCoordinates).orElse(null);

            if (piece != null && piece.getColor() == currentColor) {
                iterator.remove();
            }

            tmpBoard = BasicChessBoard.of((BasicChessBoard) board);
            tmpBoard.movePiece(move);
            ChessPieceColor opponentColor = currentColor == ChessPieceColor.WHITE
                    ? ChessPieceColor.BLACK
                    : ChessPieceColor.WHITE;

            List<ChessMove> opponentMoves = getOpponentMoves(opponentColor, tmpBoard);
            boolean isOpponentHasCheckMoves = opponentMoves.stream()
                    .map(ChessMove::getNewCoordinates)
                    .anyMatch(coordinates -> Objects.equals(coordinates, currentKingCoordinates));

            if (isOpponentHasCheckMoves) {
                iterator.remove();
            }
        }
    }

    protected List<ChessMove> getMoves(ChessPiece piece,
                                       GameBoardSquareCoordinates startCoordinates,
                                       GameBoardSquareCoordinates currentCoordinates,
                                       int deltaRow,
                                       int deltaColumn,
                                       ChessBoard board) {
        List<ChessMove> moves = new ArrayList<>();

        if (startCoordinates == currentCoordinates) {
            int row = startCoordinates.getRow();
            int column = startCoordinates.getColumn();
            GameBoardSquareCoordinates newCoordinates = GameBoardSquareCoordinates.of(row + deltaRow, column + deltaColumn);

            return getMoves(piece, startCoordinates, newCoordinates, deltaRow, deltaColumn, board);
        }

        if (!isCoordinatesInBoardSizeRange(currentCoordinates, board)) {
            return moves;
        }

        ChessPiece currentPiece = board.getPiece(currentCoordinates).orElse(null);
        int row = currentCoordinates.getRow();
        int column = currentCoordinates.getColumn();

        if (currentPiece == null) {
            GameBoardSquareCoordinates newCoordinates = GameBoardSquareCoordinates.of(row + deltaRow, column + deltaColumn);
            moves.addAll(getMoves(piece, startCoordinates, newCoordinates, deltaRow, deltaColumn, board));
            moves.add(BasicChessMove.of(piece, startCoordinates, currentCoordinates));
        } else {
            ChessPieceColor playerColor = piece.getColor();
            ChessPieceColor currentPieceColor = currentPiece.getColor();

            if (playerColor != currentPieceColor) {
                moves.add(BasicChessMove.of(piece, startCoordinates, currentCoordinates));
            }
        }

        return moves;
    }

    protected boolean isCoordinatesInBoardSizeRange(GameBoardSquareCoordinates coordinates, ChessBoard board) {
        ChessBoardSize size = board.getSize();
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        return row >= 0 && row < size.getRowsCount() && column >= 0 && column < size.getColumnsCount();
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

    private List<ChessMove> getOpponentMoves(ChessPieceColor opponentColor, ChessBoard board) {
        List<ChessMove> opponentMoves = new ArrayList<>();

        Map<GameBoardSquareCoordinates, ChessPiece> pieces = board.getPieces();

        for (Map.Entry<GameBoardSquareCoordinates, ChessPiece> entry : pieces.entrySet()) {
            GameBoardSquareCoordinates coordinates = entry.getKey();
            ChessPiece piece = entry.getValue();

            if (piece.getColor() != opponentColor) {
                continue;
            }

            List<ChessMove> moves = coordinatorMap.get(piece.getType()).getMoves(piece, coordinates, board);

            opponentMoves.addAll(moves);
        }

        return opponentMoves;
    }
}
