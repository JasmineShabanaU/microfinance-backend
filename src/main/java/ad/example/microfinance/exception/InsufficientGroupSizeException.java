package ad.example.microfinance.exception;

public class InsufficientGroupSizeException extends RuntimeException {
    public InsufficientGroupSizeException(String message) {
        super(message);
    }
}
