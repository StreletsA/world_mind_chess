package com.github.streletsa.worldmindchess.app.chess.impl;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;
import com.github.streletsa.worldmindchess.app.chess.GlobalPawnPromotion;

import java.util.Objects;

public class DefaultGlobalPawnPromotion implements GlobalPawnPromotion {
    private final GameBoardSquareCoordinates coordinates;
    private final ChessPiece piece;

    public static DefaultGlobalPawnPromotion of(GameBoardSquareCoordinates coordinates, ChessPiece piece) {
        return new DefaultGlobalPawnPromotion(coordinates, piece);
    }

    private DefaultGlobalPawnPromotion(GameBoardSquareCoordinates coordinates, ChessPiece piece) {
        this.coordinates = coordinates;
        this.piece = piece;
    }

    @Override
    public GameBoardSquareCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public ChessPiece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof GlobalPawnPromotion)) {
            return false;
        }

        GlobalPawnPromotion other = (GlobalPawnPromotion) obj;

        return Objects.equals(coordinates, other.getCoordinates()) && Objects.equals(piece, other.getPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, piece);
    }
}
