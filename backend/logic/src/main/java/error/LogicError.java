package error;

public class LogicError extends RuntimeException {
    public LogicError() {
        super();
    }

    public LogicError(String message) {
        super(message);
    }
}
