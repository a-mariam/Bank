package ambali.com.Exceptions;

public class AccountAlreadyExist extends Throwable {
    public AccountAlreadyExist(String messages) {
        super(messages);
    }
}
