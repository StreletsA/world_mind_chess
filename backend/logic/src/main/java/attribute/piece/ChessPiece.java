package attribute.piece;

import error.LogicError;

public class ChessPiece {
    private final ChessPieceType type;
    private final ChessPieceColor color;

    public static ChessPiece of(ChessPieceType type, ChessPieceColor color) {
        if (type == null) {
            throw new LogicError("Type of piece must not be null");
        } else if (color == null) {
            throw new LogicError("Color of piece must not be null");
        }

        return new ChessPiece(type, color);
    }

    private ChessPiece(ChessPieceType type, ChessPieceColor color) {
        this.type = type;
        this.color = color;
    }

    public ChessPieceType getType() {
        return type;
    }

    public ChessPieceColor getColor() {
        return color;
    }
}
