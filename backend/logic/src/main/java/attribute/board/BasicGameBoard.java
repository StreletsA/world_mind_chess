package attribute.board;

import attribute.row.BasicGameBoardRow;
import attribute.square.BasicGameBoardSquare;
import attribute.row.GameBoardRow;
import attribute.square.GameBoardSquare;
import attribute.square.GameBoardSquareCoordinates;
import error.LogicError;
import game.move.ChessMove;
import attribute.piece.ChessPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BasicGameBoard implements GameBoard {
    private static final GameBoardSize size = GameBoardSize.BASIC;
    private static final List<GameBoardRow> rows = new ArrayList<>();

    public BasicGameBoard() {
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
    public GameBoardSize getSize() {
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
}
