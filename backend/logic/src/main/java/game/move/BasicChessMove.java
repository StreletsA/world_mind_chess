package game.move;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

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
}
