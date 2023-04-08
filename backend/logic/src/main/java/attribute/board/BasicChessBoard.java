package attribute.board;

import attribute.piece.ChessPiece;
import attribute.row.BasicGameBoardRow;
import attribute.row.GameBoardRow;
import attribute.square.BasicGameBoardSquare;
import attribute.square.GameBoardSquare;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.move.ChessMove;

import java.util.*;

public class BasicChessBoard implements ChessBoard {
    private static final ChessBoardSize size = ChessBoardSize.BASIC;
    private static final List<GameBoardRow> rows = new ArrayList<>();

    public static BasicChessBoard of(BasicChessBoard otherBoard) {
        BasicChessBoard board = new BasicChessBoard();
        Map<GameBoardSquareCoordinates, ChessPiece> pieces = otherBoard.getPieces();

        for (Map.Entry<GameBoardSquareCoordinates, ChessPiece> entry : pieces.entrySet()) {
            GameBoardSquareCoordinates coordinates = entry.getKey();
            ChessPiece piece = entry.getValue();

            board.setPiece(piece, coordinates);
        }

        return board;
    }

    public BasicChessBoard() {
        for (int i = 0; i < size.getRowsCount(); i++) {
            List<GameBoardSquare> squares = new ArrayList<>();

            for (int j = 0; j < size.getColumnsCount(); j++) {
                GameBoardSquareCoordinates coordinates = GameBoardSquareCoordinates.of(i, j);

                squares.add(BasicGameBoardSquare.of(coordinates));
            }

            rows.add(BasicGameBoardRow.of(i, squares));
        }
    }

    @Override
    public ChessBoardSize getSize() {
        return size;
    }

    @Override
    public void clear() {
        rows.stream()
                .map(GameBoardRow::getSquares)
                .flatMap(List::stream)
                .forEach(GameBoardSquare::removePiece);
    }

    @Override
    public List<GameBoardRow> getRows() {
        return new ArrayList<>(rows);
    }

    @Override
    public GameBoardRow getRow(int rowIndex) throws LogicError {
        return rows.get(rowIndex);
    }

    @Override
    public GameBoardSquare getSquare(GameBoardSquareCoordinates coordinates) throws LogicError {
        if (coordinates == null) {
            throw new LogicError("Coordinates must not be null");
        }

        int row = coordinates.getRow();
        int column = coordinates.getColumn();
        
        return rows.get(row).getSquare(column);
    }

    @Override
    public void setPiece(ChessPiece piece, GameBoardSquareCoordinates coordinates) throws LogicError {
        getSquare(coordinates).setPiece(piece);
    }

    @Override
    public Optional<ChessPiece> getPiece(GameBoardSquareCoordinates coordinates) {
        return getSquare(coordinates).getPiece();
    }

    @Override
    public Map<GameBoardSquareCoordinates, ChessPiece> getPieces() {
        Map<GameBoardSquareCoordinates, ChessPiece> pieceMap = new HashMap<>();

        for (GameBoardRow row : rows) {
            for (GameBoardSquare square : row.getSquares()) {
                square.getPiece().ifPresent(chessPiece -> pieceMap.put(square.getCoordinates(), chessPiece));
            }
        }

        return pieceMap;
    }

    @Override
    public void removePiece(GameBoardSquareCoordinates coordinates) throws LogicError {
        getSquare(coordinates).removePiece();
    }

    @Override
    public Optional<ChessPiece> movePiece(ChessMove move) {
        GameBoardSquareCoordinates oldCoordinates = move.getOldCoordinates();
        GameBoardSquareCoordinates newCoordinates = move.getNewCoordinates();
        ChessPiece piece = move.getPiece();

        GameBoardSquare oldSquare = getSquare(oldCoordinates);
        GameBoardSquare newSquare = getSquare(newCoordinates);

        if (oldSquare.isEmpty()) {
            throw new LogicError(String.format("Square [%s] is empty", oldCoordinates.getStandardNotation()));
        }

        ChessPiece oldSquarePiece = oldSquare.getPiece().orElse(null);

        assert oldSquarePiece != null;

        if (!Objects.equals(oldSquarePiece, piece)) {
            throw new LogicError(String.format("Square [%s] has piece [%s:%s] but trying to move piece [%s:%s]",
                    oldCoordinates.getStandardNotation(),
                    oldSquarePiece.getType().name(),
                    oldSquarePiece.getColor().name(),
                    piece.getType().name(),
                    piece.getColor().name()));
        }

        Optional<ChessPiece> removedPiece = Optional.empty();

        if (!newSquare.isEmpty()) {
            removedPiece = Optional.of(newSquare.removePiece());
        }

        oldSquare.removePiece();
        newSquare.setPiece(piece);

        return removedPiece;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ChessBoard)) {
            return false;
        }

        ChessBoard other = (ChessBoard) obj;

        return Objects.equals(size, other.getSize())
                && Objects.equals(rows, other.getRows());
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, rows);
    }
}
