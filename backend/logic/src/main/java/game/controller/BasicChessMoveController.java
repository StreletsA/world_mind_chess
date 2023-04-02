package game.controller;

import game.move.ChessMove;
import game.player.Player;

import java.util.List;

public class BasicChessMoveController implements ChessMoveController {
    @Override
    public boolean isMoveAvailable(ChessMove chessMove) {
        return false;
    }

    @Override
    public List<ChessMove> getAvailableMovesForPlayer(Player player) {
        return null;
    }
}
