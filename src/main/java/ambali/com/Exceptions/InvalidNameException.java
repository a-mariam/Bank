package ambali.com.Exceptions;

public class InvalidNameException extends IllegalArgumentException {
    public InvalidNameException(String messages) {
        super(messages);
    }
}
