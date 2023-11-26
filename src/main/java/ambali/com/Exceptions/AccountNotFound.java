package ambali.com.Exceptions;

import java.util.prefs.BackingStoreException;

public class AccountNotFound extends BackingStoreException {
public AccountNotFound(String messages){
    super(messages);
}
}
