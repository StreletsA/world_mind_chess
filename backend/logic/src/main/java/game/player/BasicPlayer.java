package game.player;

import attribute.piece.ChessPieceColor;
import game.ChessGame;
import game.move.BasicPlayerChessMove;
import game.move.ChessMove;

public class BasicPlayer implements Player {
    private final ChessPieceColor color;
    private final ChessGame game;

    public static BasicPlayer of(ChessPieceColor color, ChessGame game) {
        return new BasicPlayer(color, game);
    }

    private BasicPlayer(ChessPieceColor color, ChessGame game) {
        this.color = color;
        this.game = game;
    }

    @Override
    public ChessPieceColor getColor() {
        return color;
    }

    @Override
    public ChessGame getGame() {
        return game;
    }

    @Override
    public void movePiece(ChessMove move) {
        game.movePiece(BasicPlayerChessMove.of(this, move));
    }
}
