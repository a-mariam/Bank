package ambali.com.Exceptions;

public class IncorrectInputException extends BankTransactionException {
    public IncorrectInputException(String messages) {
        super(messages);
    }
}
