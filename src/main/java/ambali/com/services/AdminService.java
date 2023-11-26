package ambali.com.services;

import ambali.com.Exceptions.AccountNotFound;
import ambali.com.Exceptions.InvalidPhoneNumberException;
import ambali.com.Exceptions.UserAlreadyExist;
import ambali.com.dtos.request.CreateUser;
import ambali.com.model.Account;
import ambali.com.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Account findByAccountNumber(String accountNumber) throws AccountNotFound;
    Account saveAccount(Account account);
    Account findByAccountPhoneNumber(String phoneNumber);


    User createUser(CreateUser createUser) throws UserAlreadyExist, InvalidPhoneNumberException;

    User findUserByPhoneNumber(String phoneNumber);

    User saveUser(User user);
}
