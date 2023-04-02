package game.move;

import game.move.ChessMove;
import game.player.Player;

public interface PlayerChessMove {
    Player getPlayer();
    ChessMove getMove();
}
