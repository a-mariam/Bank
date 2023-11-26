package mapper;

import ambali.com.Exceptions.BankTransactionException;
import ambali.com.Exceptions.InvalidPhoneNumberException;
import ambali.com.dtos.request.CreateAccountRequest;
import ambali.com.dtos.request.CreateUser;
import ambali.com.dtos.response.CreateUseResponse;
import ambali.com.model.Account;
import ambali.com.model.User;
import org.springframework.stereotype.Component;

import static ambali.com.validation.AccountServiceValidation.*;

@Component
public class Mapper  {


    public static  Account map(CreateAccountRequest createAccountRequest) throws InvalidPhoneNumberException {
            Account account = new Account();
                account.setFirstName(createAccountRequest.getFirstName());
                account.setSecondName(createAccountRequest.getSecondName());
                account.setLastName(createAccountRequest.getLastName());
            if (validatePhoneNumber(createAccountRequest.getPhoneNumber())) {
                account.setPhoneNumber(createAccountRequest.getPhoneNumber());
            } else {
                throw new InvalidPhoneNumberException(" Invalid Phone Number");
            }
            setAccountType(createAccountRequest.getType(), account);
            account.setEmail(createAccountRequest.getEmail()) ;
            account.setAccountNumber(generateAccountNumber(createAccountRequest.getPhoneNumber()));
            account.setPin(createAccountRequest.getPin());
            return account;

    }

    public static User map(CreateUser createUser)  {
        boolean validate  = validatePhoneNumber(createUser.getPhoneNumber());
        if (validate){
            User user = new User();
            user.setPassword(createUser.getPassword());
            user.setPhoneNumber(createUser.getPhoneNumber());
            return user;
        }else throw new BankTransactionException( "invalid phone number");

    }
    public static Account maps(CreateUser createUser) throws InvalidPhoneNumberException {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setPin(createUser.getPin());
        createAccountRequest.setType(createUser.getType());
        createAccountRequest.setPhoneNumber(createUser.getPhoneNumber());
        createAccountRequest.setSecondName(createUser.getSecondName());
        createAccountRequest.setEmail(createUser.getEmail());
        createAccountRequest.setLastName(createUser.getLastName());
        createAccountRequest.setSecondName(createUser.getSecondName());

        return map(createAccountRequest);

    }

    public static CreateUseResponse map(User user) {
       Account account =  user.getAccount();
       CreateUseResponse response = new CreateUseResponse();
       response.setBalance(account.getBalance().toString());
       response.setFirstName(account.getFirstName());
       response.setAccountType(account.getType().toString());
       response.setEmail(account.getEmail());
       response.setLastName(account.getLastName());
       response.setAccountNumber(account.getAccountNumber());
       response.setPhoneNumber(account.getPhoneNumber());
       response.setSecondName(account.getSecondName());
       return response;

    }


}
