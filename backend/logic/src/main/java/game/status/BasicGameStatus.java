package game.status;

import error.LogicError;
import game.player.Player;

public class BasicGameStatus implements GameStatus {
    public static final BasicGameStatus START_GAME_STATUS = BasicGameStatus.of(false, false, null);

    private final boolean gameOver;
    private final boolean draw;
    private final Player winner;

    public static BasicGameStatus of(boolean gameOver, boolean draw, Player winner) {
        return new BasicGameStatus(gameOver, draw, winner);
    }

    private BasicGameStatus(boolean gameOver, boolean draw, Player winner) {
        this.gameOver = gameOver;
        this.draw = draw;
        this.winner = winner;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isDraw() {
        return draw;
    }

    @Override
    public Player getWinner() {
        if (!isGameOver() || isDraw()) {
            throw new LogicError("Game has not winner");
        }

        return winner;
    }
}
