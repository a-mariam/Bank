package ambali.com.validation;

import ambali.com.Exceptions.AccountNotFound;
import ambali.com.Exceptions.IncorrectInputException;
import ambali.com.Exceptions.InsufficientException;
import ambali.com.Exceptions.UnknownAccountTypeException;
import ambali.com.dtos.request.CreateUser;
import ambali.com.dtos.request.ValidateWithdrawRequest;
import ambali.com.dtos.request.WithdrawRequest;
import ambali.com.model.Account;
import ambali.com.model.AccountType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountServiceValidation {

//    }
    public static void setAccountType(String accountType, Account account) {
        if (AccountType.EQUITY.name().equalsIgnoreCase(accountType)) {
            account.setType(AccountType.EQUITY);
        } else if (AccountType.ASSETS.name().equalsIgnoreCase(accountType)) {
            account.setType(AccountType.ASSETS);
        } else if (AccountType.REVENUE.name().equalsIgnoreCase(accountType)) {
            account.setType(AccountType.REVENUE);
        } else if (AccountType.EXPENSES.name().equalsIgnoreCase(accountType)) {
            account.setType(AccountType.EXPENSES);
        } else if (AccountType.LIABILITIES.name().equalsIgnoreCase(accountType)) {
            account.setType(AccountType.LIABILITIES);
        } else throw new UnknownAccountTypeException("Account type entered does not exist");

    }



    public  boolean validateTransferRequest(ValidateWithdrawRequest request) throws AccountNotFound, IncorrectInputException {
        if(request.getSenderAccount() != null) {
            System.out.println("sender not null");
            if (request.getSenderAccount().getPin().equals(request.getPin())) {
                System.out.println("correct pin");
                if (request.getReceiverAccount() != null) {
                    System.out.println("receiver not null");
                    BigDecimal amountSending = BigDecimal.valueOf(Double.parseDouble(request.getAmount()));
                    if (request.getReceiverAccount().getBalance().compareTo(amountSending) >= 0) {

                        System.out.println("e get here");
                       return true;
                    }throw new InsufficientException("Insufficient balance");
                }throw new AccountNotFound("receiver account not found");
            }throw new IncorrectInputException("Incorrect pin");
        }throw new AccountNotFound("sender account not found");
    }

    public static boolean validateFirstName(String firstName){
        String regex = "[a-zA-Z]+";
        boolean isValid = firstName.matches(regex);
        return isValid;
    }
    public static boolean validateSecondName(String secondName) {
        String regex = "[a-zA-Z]+";
        boolean isValid = secondName.matches(regex);
        return isValid;
    }
    public static boolean validateLastName(String lastName){
        String regex = "[a-zA-Z]+";
        boolean isValid = lastName.matches(regex);
        return isValid;
    }
    public static boolean validatePhoneNumber(String phoneNumber){
        String wait  = "((^+234){1}[0-9]{9})";
        String regex = "(^0)(7|8|9){1}(0|1){1}[0-9]{8}|((^234){1}[0-9]{9})";
        boolean isValid = phoneNumber.matches(regex);
        return isValid;
    }
    public static String generateAccountNumber(String phoneNumber){
        String accountNumber = "";
        for(int count = 1; count < phoneNumber.length(); count++){
            accountNumber += phoneNumber.charAt(count);
        }
        return accountNumber;
    }



}
