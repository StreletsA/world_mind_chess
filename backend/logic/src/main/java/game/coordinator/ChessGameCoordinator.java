package game.coordinator;

import attribute.board.ChessBoard;
import attribute.square.GameBoardSquareCoordinates;
import game.move.ChessMove;
import game.player.Player;

import java.util.List;

public interface ChessGameCoordinator {
    List<ChessMove> getAllowedMoves(Player player, ChessBoard board);
    List<ChessMove> getAllowedMoves(Player player, GameBoardSquareCoordinates startCoordinates, ChessBoard board);
    List<ChessMove> getChecksForPlayer(Player player, ChessBoard board);
}
