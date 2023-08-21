package ambali.com.Exceptions;

public class InvalidPhoneNumberException extends Throwable {
    public InvalidPhoneNumberException(String messages) {
        super(messages);
    }
}
