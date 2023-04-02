package game;

import attribute.board.BasicGameBoard;
import attribute.board.GameBoard;
import attribute.piece.ChessPieceColor;
import game.player.BasicPlayer;
import game.player.Player;

public class ChessGameFactoryImpl implements ChessGameFactory {
    @Override
    public ChessGame createBasicGame() {
        GameBoard board = new BasicGameBoard();
        BasicChessGame game = BasicChessGame.of(board);

        Player playerWhite = BasicPlayer.of(ChessPieceColor.WHITE, game);
        Player playerBlack = BasicPlayer.of(ChessPieceColor.BLACK, game);

        game.setPlayerWhite(playerWhite);
        game.setPlayerBlack(playerBlack);

        return game;
    }
}
