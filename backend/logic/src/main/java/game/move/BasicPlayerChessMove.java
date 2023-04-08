package game.move;

import game.player.Player;

import java.util.Objects;

public class BasicPlayerChessMove implements PlayerChessMove {
    private final Player player;
    private final ChessMove chessMove;

    public static BasicPlayerChessMove of(Player player, ChessMove chessMove) {
        return new BasicPlayerChessMove(player, chessMove);
    }

    private BasicPlayerChessMove(Player player, ChessMove chessMove) {
        this.player = player;
        this.chessMove = chessMove;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public ChessMove getMove() {
        return chessMove;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PlayerChessMove)) {
            return false;
        }

        PlayerChessMove other = (PlayerChessMove) obj;

        return Objects.equals(player, other.getPlayer())
                && Objects.equals(chessMove, other.getMove());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, chessMove);
    }
}
