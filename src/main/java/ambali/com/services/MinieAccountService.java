package ambali.com.services;

import ambali.com.Exceptions.AccountAlreadyExist;
import ambali.com.Exceptions.InvalidNameException;
import ambali.com.Exceptions.InvalidPhoneNumberException;
import ambali.com.Exceptions.UnknownAccountTypeException;
import ambali.com.dtos.CreateAccountRequest;
import ambali.com.model.Account;
import ambali.com.model.AccountType;
import ambali.com.repositories.MinieAccountsRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinieAccountService implements AccountServices{
@Autowired
private MinieAccountsRepository minieAccountsRepository;
    @SneakyThrows
    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        if (checkIfAccountAlreadyExist(createAccountRequest.getPhoneNumber())== false){
            Account account = new Account();
        if (validateFirstName(createAccountRequest.getFirstName())) {
            account.setFirstName(createAccountRequest.getFirstName());
        } else {
            throw new InvalidNameException("Invalid first Name");
        }
        if (validateSecondName(createAccountRequest.getSecondName())) {
            account.setSecondName(createAccountRequest.getSecondName());
        } else {
            throw new InvalidNameException("Invalid second Name");
        }
        if (validateLastName(createAccountRequest.getLastName())) {
            account.setLastName(createAccountRequest.getLastName());
        } else {
            throw new InvalidNameException("Invalid last Name");
        }
        if (validatePhoneNumber(createAccountRequest.getPhoneNumber())) {
            account.setPhoneNumber(createAccountRequest.getPhoneNumber());
        } else {
            throw new InvalidPhoneNumberException("Invalid Phone Number");
        }
        setAccountType(createAccountRequest.getType(), account);
        account.setAccountNumber(generateAccountNumber(createAccountRequest.getPhoneNumber()));
        account.setPin(createAccountRequest.getPin());
        Account savedAccount = minieAccountsRepository.save(account);
        return savedAccount;
    }
        throw new AccountAlreadyExist("Phone Number is already used by other user");
    }
    @SneakyThrows
    public boolean checkIfAccountAlreadyExist(String phoneNumber){
        Account doesAccountExist = minieAccountsRepository.findByPhoneNumber(phoneNumber);
        if(doesAccountExist != null){
          //  throw new AccountAlreadyExist("Phone Number is already use by other user");
            return true;
        }
        return false;
    }
    public String generateAccountNumber(String phoneNumber){
        String accountNumber = "";
        for(int count = 1; count < phoneNumber.length(); count++){
            accountNumber += phoneNumber.charAt(count);
        }
        return accountNumber;
    }
   public void setAccountType(String accountType, Account account){
       if (AccountType.EQUITY.name().equalsIgnoreCase(accountType)){
           account.setType(AccountType.EQUITY);
       }else if(AccountType.ASSETS.name().equalsIgnoreCase(accountType)){
           account.setType(AccountType.ASSETS);
       }else if(AccountType.REVENUE.name().equalsIgnoreCase(accountType)){
           account.setType(AccountType.REVENUE);
       }else if(AccountType.EXPENSES.name().equalsIgnoreCase(accountType)){
           account.setType(AccountType.EXPENSES);
       }else if(AccountType.LIABILITIES.name().equalsIgnoreCase(accountType)){
           account.setType(AccountType.LIABILITIES);
       }else throw new UnknownAccountTypeException("Account type entered does not exist");

   }
   public boolean validateFirstName(String firstName){
        String regex = "[a-zA-Z]+";
        boolean isValid = firstName.matches(regex);
        return isValid;
   }
    public boolean validateSecondName(String secondName){
        String regex = "[a-zA-Z]+";
        boolean isValid = secondName.matches(regex);
        return isValid;
    }
    public boolean validateLastName(String lastName){
        String regex = "[a-zA-Z]+";
        boolean isValid = lastName.matches(regex);
        return isValid;
    }
    public boolean validatePhoneNumber(String phoneNumber){
        String wait  = "((^+234){1}[0-9]{9})";
        String regex = "(^0)(7|8|9){1}(0|1){1}[0-9]{8}|((^234){1}[0-9]{9})";
        boolean isValid = phoneNumber.matches(regex);
        return isValid;
    }

    public long accountsCount() {
      return minieAccountsRepository.count();
    }
}
