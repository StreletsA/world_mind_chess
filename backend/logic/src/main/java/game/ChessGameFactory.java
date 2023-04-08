package game;

import game.player.Player;

public interface ChessGameFactory {
    ChessGame createBasicGame();
    ChessGame createBasicGame(Player whitePlayer, Player blackPlayer);
}
