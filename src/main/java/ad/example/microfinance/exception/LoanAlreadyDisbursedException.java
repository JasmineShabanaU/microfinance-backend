package ad.example.microfinance.exception;

public class LoanAlreadyDisbursedException extends RuntimeException {
    public LoanAlreadyDisbursedException(String message) {
        super(message);
    }
}
