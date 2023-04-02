package game.controller;

import game.move.ChessMove;
import game.player.Player;

import java.util.List;

public interface ChessMoveController {
    boolean isMoveAvailable(ChessMove chessMove);
    List<ChessMove> getAvailableMovesForPlayer(Player player);
}
