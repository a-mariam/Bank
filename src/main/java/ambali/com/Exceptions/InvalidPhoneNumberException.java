package ambali.com.Exceptions;

public class InvalidPhoneNumberException extends BankTransactionException{
    public InvalidPhoneNumberException(String messages) {
        super(messages);
    }
}
