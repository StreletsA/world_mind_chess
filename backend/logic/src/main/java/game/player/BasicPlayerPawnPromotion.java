package game.player;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

public class BasicPlayerPawnPromotion implements PlayerPawnPromotion {
    private final Player player;
    private final GameBoardSquareCoordinates coordinates;
    private final ChessPiece piece;

    public static BasicPlayerPawnPromotion of(Player player, GameBoardSquareCoordinates coordinates, ChessPiece piece) {
        return new BasicPlayerPawnPromotion(player, coordinates, piece);
    }

    private BasicPlayerPawnPromotion(Player player, GameBoardSquareCoordinates coordinates, ChessPiece piece) {
        this.player = player;
        this.coordinates = coordinates;
        this.piece = piece;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public GameBoardSquareCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public ChessPiece getPiece() {
        return piece;
    }
}
