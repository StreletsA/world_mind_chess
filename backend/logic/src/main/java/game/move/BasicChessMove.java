package game.move;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

import java.util.Objects;

public class BasicChessMove implements ChessMove {
    private final ChessPiece piece;
    private final GameBoardSquareCoordinates oldCoordinates;
    private final GameBoardSquareCoordinates newCoordinates;

    public static BasicChessMove of(ChessPiece piece,
                                    GameBoardSquareCoordinates oldCoordinates,
                                    GameBoardSquareCoordinates newCoordinates) {
        return new BasicChessMove(piece, oldCoordinates, newCoordinates);
    }

    private BasicChessMove(ChessPiece piece,
                           GameBoardSquareCoordinates oldCoordinates,
                           GameBoardSquareCoordinates newCoordinates) {
        this.piece = piece;
        this.oldCoordinates = oldCoordinates;
        this.newCoordinates = newCoordinates;
    }

    @Override
    public ChessPiece getPiece() {
        return piece;
    }

    @Override
    public GameBoardSquareCoordinates getOldCoordinates() {
        return oldCoordinates;
    }

    @Override
    public GameBoardSquareCoordinates getNewCoordinates() {
        return newCoordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ChessMove)) {
            return false;
        }

        ChessMove other = (ChessMove) obj;

        return Objects.equals(piece, other.getPiece())
                && Objects.equals(oldCoordinates, other.getOldCoordinates())
                && Objects.equals(newCoordinates, other.getNewCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, oldCoordinates, newCoordinates);
    }
}
