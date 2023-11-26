package ambali.com.services;

import ambali.com.Exceptions.*;
import ambali.com.dtos.request.*;
import ambali.com.dtos.response.CreateUseResponse;
import ambali.com.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    long getUserCount();

    CreateUseResponse createUse(CreateUser createUser) throws UserAlreadyExist, InvalidPhoneNumberException;

    Account createAccount(CreateAccountRequest createAccountRequest) throws InvalidPhoneNumberException, AccountAlreadyExist;

    String withdraw(WithdrawRequest withdrawRequest) throws IncorrectInputException, AccountNotFound;

    void deposit(DepositRequest depositRequest) throws AccountNotFound;

    void transfer(TransferRequest transferRequest) throws IncorrectInputException, AccountNotFound;

    String getBalance(GetBalanceRequest request) throws AccountNotFound;
}
