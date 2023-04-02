package attribute.square;

import error.LogicError;
import attribute.piece.ChessPiece;

import java.util.Optional;

public class BasicGameBoardSquare implements GameBoardSquare {
    private final GameBoardSquareCoordinates coordinates;

    private ChessPiece piece;

    public static BasicGameBoardSquare of(GameBoardSquareCoordinates coordinates) {
        return new BasicGameBoardSquare(coordinates);
    }

    private BasicGameBoardSquare(GameBoardSquareCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GameBoardSquareCoordinates getCoordinates() {
        return coordinates;
    }

    public void setPiece(ChessPiece piece) {
        if (!isEmpty()) {
            throw new LogicError("Square is busy");
        }

        this.piece = piece;
    }

    public Optional<ChessPiece> getPiece() {
        return Optional.ofNullable(piece);
    }

    public ChessPiece removePiece() {
        if (isEmpty()) {
            throw new LogicError("Square is already empty");
        }

        ChessPiece removedPiece = piece;

        this.piece = null;

        return removedPiece;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
