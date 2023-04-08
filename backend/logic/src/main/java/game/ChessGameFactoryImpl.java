package game;

import attribute.board.BasicChessBoard;
import attribute.board.ChessBoard;
import attribute.piece.ChessPieceColor;
import attribute.piece.ChessPieceType;
import game.coordinator.BasicChessGameCoordinator;
import game.coordinator.piece.*;
import game.player.BasicPlayer;
import game.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ChessGameFactoryImpl implements ChessGameFactory {
    private static final Map<ChessPieceType, BasicChessPieceCoordinator> BASIC_PIECE_COORDINATORS = new HashMap<>();

    static {
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.BISHOP, new BishopPieceCoordinator(BASIC_PIECE_COORDINATORS));
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.KING, new KingPieceCoordinator(BASIC_PIECE_COORDINATORS));
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.KNIGHT, new KnightCoordinator(BASIC_PIECE_COORDINATORS));
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.PAWN, new PawnCoordinator(BASIC_PIECE_COORDINATORS));
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.QUEEN, new QueenPieceCoordinator(BASIC_PIECE_COORDINATORS));
        BASIC_PIECE_COORDINATORS.put(ChessPieceType.ROOK, new RookPieceCoordinator(BASIC_PIECE_COORDINATORS));
    }

    @Override
    public ChessGame createBasicGame() {
        Player playerWhite = BasicPlayer.of(ChessPieceColor.WHITE);
        Player playerBlack = BasicPlayer.of(ChessPieceColor.BLACK);

        return createBasicGame(playerWhite, playerBlack);
    }

    @Override
    public ChessGame createBasicGame(Player playerWhite, Player playerBlack) {
        ChessBoard board = new BasicChessBoard();
        BasicChessGameCoordinator gameCoordinator = new BasicChessGameCoordinator(BASIC_PIECE_COORDINATORS);
        BasicChessGame game = BasicChessGame.of(board, gameCoordinator);

        game.setPlayerWhite(playerWhite);
        game.setPlayerBlack(playerBlack);

        return game;
    }
}
