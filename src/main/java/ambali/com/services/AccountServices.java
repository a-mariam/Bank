package ambali.com.services;

import ambali.com.dtos.CreateAccountRequest;
import ambali.com.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountServices {
    Account createAccount(CreateAccountRequest createAccountRequest);

}
