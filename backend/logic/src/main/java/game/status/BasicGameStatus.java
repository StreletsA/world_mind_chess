package game.status;

import error.LogicError;
import game.player.Player;

import java.util.Objects;

public class BasicGameStatus implements GameStatus {
    public static final BasicGameStatus START_GAME_STATUS = BasicGameStatus.of(false, false, null);
    public static final BasicGameStatus DRAW_STATUS = BasicGameStatus.of(true, true, null);

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof GameStatus)) {
            return false;
        }

        GameStatus other = (GameStatus) obj;

        return Objects.equals(gameOver, other.isGameOver())
                && Objects.equals(draw, other.isDraw())
                && Objects.equals(winner, other.getWinner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameOver, draw, winner);
    }
}
