package com.github.streletsa.worldmindchess.app.chess;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

public interface GlobalPawnPromotion {
    GameBoardSquareCoordinates getCoordinates();
    ChessPiece getPiece();
}
