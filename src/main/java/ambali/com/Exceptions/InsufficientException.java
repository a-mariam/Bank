package ambali.com.Exceptions;

public class InsufficientException extends BankTransactionException{

    public InsufficientException(String message) {
        super(message);
    }
}
