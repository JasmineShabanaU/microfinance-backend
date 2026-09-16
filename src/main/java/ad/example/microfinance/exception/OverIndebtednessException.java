package ad.example.microfinance.exception;

public class OverIndebtednessException extends RuntimeException {
    public OverIndebtednessException(String message) {
        super(message);
    }
}
