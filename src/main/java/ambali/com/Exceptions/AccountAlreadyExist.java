package ambali.com.Exceptions;

public class AccountAlreadyExist extends BankTransactionException {
    public AccountAlreadyExist(String messages) {
        super( messages);
    }
}
