package com.github.streletsa.worldmindchess.app.chess;

import game.move.ChessMove;

import java.util.Objects;

public class ChessMoveVote {
    private final String login;
    private final ChessMove move;

    public static ChessMoveVote of(String login, ChessMove move) {
        return new ChessMoveVote(login, move);
    }

    private ChessMoveVote(String login, ChessMove move) {
        this.login = login;
        this.move = move;
    }

    public String getLogin() {
        return login;
    }

    public ChessMove getMove() {
        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ChessMoveVote)) {
            return false;
        }

        ChessMoveVote other = (ChessMoveVote) obj;

        return Objects.equals(login, other.login) && Objects.equals(move, other.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, move);
    }
}
