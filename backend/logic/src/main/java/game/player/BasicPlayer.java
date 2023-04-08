package game.player;

import attribute.piece.ChessPieceColor;
import game.ChessGame;
import game.move.BasicPlayerChessMove;
import game.move.ChessMove;

import java.util.Objects;

public class BasicPlayer implements Player {
    private final ChessPieceColor color;
    private ChessGame game;

    public static BasicPlayer of(ChessPieceColor color) {
        return new BasicPlayer(color, null);
    }

    public static BasicPlayer of(ChessPieceColor color, ChessGame game) {
        return new BasicPlayer(color, game);
    }

    private BasicPlayer(ChessPieceColor color, ChessGame game) {
        this.color = color;
        this.game = game;
    }

    @Override
    public void setGame(ChessGame game) {
        this.game = game;

        if (color == ChessPieceColor.WHITE) {
            game.setPlayerWhite(this);
        } else {
            game.setPlayerBlack(this);
        }
    }

    @Override
    public ChessPieceColor getColor() {
        return color;
    }

    @Override
    public ChessGame getGame() {
        return game;
    }

    @Override
    public void movePiece(ChessMove move) {
        game.movePiece(BasicPlayerChessMove.of(this, move));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BasicPlayer)) {
            return false;
        }

        BasicPlayer other = (BasicPlayer) obj;

        return Objects.equals(color, other.color)
                && Objects.equals(game, other.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, game);
    }
}
