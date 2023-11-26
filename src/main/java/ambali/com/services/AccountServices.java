package ambali.com.services;

import ambali.com.Exceptions.AccountAlreadyExist;
import ambali.com.Exceptions.AccountNotFound;
import ambali.com.Exceptions.IncorrectInputException;
import ambali.com.Exceptions.InvalidPhoneNumberException;
import ambali.com.dtos.request.CreateAccountRequest;
import ambali.com.dtos.request.TransferRequest;
import ambali.com.dtos.request.WithdrawRequest;
import ambali.com.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountServices {
    Account createAccount(CreateAccountRequest createAccountRequest) throws InvalidPhoneNumberException, AccountAlreadyExist;
    String withdraw(WithdrawRequest request) throws IncorrectInputException, AccountNotFound;
    void deposit(String accountNumber, String amount) throws AccountNotFound;

    void transfer(TransferRequest request) throws AccountNotFound, IncorrectInputException;
    BigDecimal checkBalance(String AccountNumber, String pin) throws AccountNotFound, IncorrectInputException;
}
