package attribute.square;

import attribute.piece.ChessPiece;
import error.LogicError;

import java.util.Objects;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof GameBoardSquare)) {
            return false;
        }

        GameBoardSquare other = (GameBoardSquare) obj;

        return Objects.equals(coordinates, other.getCoordinates())
                && Objects.equals(piece, other.getPiece().orElse(null));
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, piece);
    }
}
