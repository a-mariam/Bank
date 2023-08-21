package ambali.com.Exceptions;

public class UnknownAccountTypeException extends IllegalArgumentException{
    public UnknownAccountTypeException(String messages){
        super(messages);
    }

}
