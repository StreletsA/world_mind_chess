package game.move;

import game.player.Player;

public interface PlayerChessMove {
    Player getPlayer();
    ChessMove getMove();
}
