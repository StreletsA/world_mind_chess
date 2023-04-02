package game.move;

import game.player.Player;

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
}
