package game.player;

import attribute.piece.ChessPiece;
import attribute.square.GameBoardSquareCoordinates;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PlayerPawnPromotion)) {
            return false;
        }

        PlayerPawnPromotion other = (PlayerPawnPromotion) obj;

        return Objects.equals(player, other.getPlayer())
                && Objects.equals(coordinates, other.getCoordinates())
                && Objects.equals(piece, other.getPiece());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, coordinates, piece);
    }
}
