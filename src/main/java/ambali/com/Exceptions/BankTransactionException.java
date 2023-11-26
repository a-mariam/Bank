package ambali.com.Exceptions;

import org.springframework.http.HttpStatus;

public class BankTransactionException extends RuntimeException{

    public BankTransactionException(String message) {
        super(message);
    }

    public BankTransactionException(HttpStatus conflict, String message) {
        super(message);
    }

    public BankTransactionException(Throwable cause) {
        super(cause);
    }
}
