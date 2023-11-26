package ambali.com.Exceptions;

public class UnknownAccountTypeException extends BankTransactionException{
    public UnknownAccountTypeException(String messages){
        super( messages);
    }

}
