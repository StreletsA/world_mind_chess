package com.github.streletsa.worldmindchess.app.error;

public class AppError extends RuntimeException {
    public AppError() {
        super();
    }

    public AppError(String message) {
        super(message);
    }
}
