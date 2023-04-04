package game.status;

import game.player.Player;

public interface GameStatus {
    boolean isGameOver();
    boolean isDraw();
    Player getWinner();
}
