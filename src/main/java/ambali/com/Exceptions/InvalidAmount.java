package ambali.com.Exceptions;

public class InvalidAmount extends BankTransactionException {
    public InvalidAmount(String message) {
        super( message);
    }
}
