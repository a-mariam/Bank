package ambali.com.Exceptions;

public class UserAlreadyExist extends BankTransactionException {
    public UserAlreadyExist(String s) {
        super( s);
    }
}
