package ambali.com.services;

import ambali.com.Exceptions.*;
import ambali.com.dtos.request.CreateAccountRequest;
import ambali.com.dtos.request.TransferRequest;
import ambali.com.dtos.request.WithdrawRequest;
import ambali.com.model.Account;
import ambali.com.validation.AccountServiceValidation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static mapper.Mapper.map;

@Service
public class MinieAccountService implements AccountServices{

private final AppAdminService appAdminService;

private final AccountServiceValidation validator;

    public MinieAccountService(AppAdminService appAdminService, AccountServiceValidation validator) {
        this.appAdminService = appAdminService;
        this.validator = validator;
    }



    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) throws InvalidPhoneNumberException, AccountAlreadyExist {
        if (!checkIfAccountAlreadyExist(createAccountRequest.getPhoneNumber())) {
            Account account1 = map(createAccountRequest);
            appAdminService.saveAccount(new Account());
            return appAdminService.saveAccount(account1);
        }throw new AccountAlreadyExist("account with this number already exist");
    }
    public boolean checkIfAccountAlreadyExist(String phoneNumber){
        Account doesAccountExist = appAdminService.findByAccountPhoneNumber(phoneNumber);
        if(doesAccountExist != null){
            return true;
        }
        return false;
    }

    public Account findAccount_ByAccountNumber(String accountNumber) throws AccountNotFound {
        if (appAdminService.findByAccountNumber(accountNumber)!= null) {
            return appAdminService.findByAccountNumber(accountNumber);

        }
        throw new AccountNotFound("Incorrect account number");
    }
    public void deposit( String accountNumber,String amount ) throws AccountNotFound{
        Account  fondAccount = appAdminService.findByAccountNumber(accountNumber);
        System.out.println("FoundIt :: "+ fondAccount);
        BigDecimal convertedAmount = new BigDecimal(amount);
        double check = Double.parseDouble(amount);
       if(fondAccount != null) {
           if ( check > 0) {
               fondAccount.setBalance(convertedAmount);
               appAdminService.saveAccount(fondAccount);
               System.out.println("Balnce = " + fondAccount.getBalance());
               System.out.println(fondAccount.getAccountNumber());
               System.out.println(fondAccount + "after depositing");
           } else {
               throw new InvalidAmount
                       ("Enter amount greater than 0");
           }
       }else{throw  new AccountNotFound("Account not found");}
    }

    @Override
    public void transfer(TransferRequest request) throws AccountNotFound, IncorrectInputException {
        Account senderAccount = appAdminService.findByAccountNumber(request.getSenderAccountNumber());
        System.out.println("SENDER :: " + senderAccount);
        Account receiverAccount = appAdminService.findByAccountNumber(request.getReceiverAccountNumber());
        System.out.println("RECEIVER :: " + receiverAccount);
        BigDecimal amountSending = new BigDecimal(request.getAmount());

        if(senderAccount == null) { throw new AccountNotFound("sender account not found");};
        System.out.println("sender not null");
        if (!senderAccount.getPin().equals(request.getPin())) {throw new IncorrectInputException("Incorrect pin");}
                System.out.println("correct pin");
        if (receiverAccount == null) {throw new AccountNotFound("receiver account not found");}
                    System.out.println("receiver not null");

                    if (senderAccount.getBalance().compareTo(amountSending) >= 0) {
                        if (amountSending.intValue() > 0) {
                            System.out.println("accurate amount");
                            BigDecimal amount = senderAccount.withdraw(amountSending);
                            receiverAccount.setBalance(amount);
                            appAdminService.saveAccount(senderAccount);
                            appAdminService.saveAccount(receiverAccount);
                            System.out.println("e get here");
                            System.out.println("Sender after " + senderAccount);
                            System.out.println("receiver after " + receiverAccount);
                        }else throw new InvalidAmount("Amount has to be greater than 0");
                    }else throw new InsufficientException("Insufficient balance");





    }

    @Override
    public BigDecimal checkBalance(String AccountNumber, String pin) throws AccountNotFound, IncorrectInputException {
        Account foundAccount = appAdminService.findByAccountNumber(AccountNumber);
        if (foundAccount != null){
            if(pin.equals(foundAccount.getPin())){
                return foundAccount.getBalance();
            }throw new IncorrectInputException("Incorrect pin") ;
        }throw new AccountNotFound("Account does not Exist");
    }

    public String withdraw(WithdrawRequest request) throws IncorrectInputException, AccountNotFound {
        Account foundAccount = appAdminService.findByAccountNumber(request.getAccountNumber());
        BigDecimal convertedAmount = new  BigDecimal(request.getAmount());
        if (foundAccount != null){
        if(request.getPin().equals(foundAccount.getPin())){
            if (convertedAmount.intValue() > 0){
            if (foundAccount.getBalance().compareTo(convertedAmount ) >= 0 ){
               foundAccount.withdraw(convertedAmount);
               appAdminService.saveAccount(foundAccount);
                return " withdraw was successfull";
            }else {throw new IncorrectInputException("Invalid amount");}
            }else {throw new IncorrectInputException("Invalid amount");}
        }else{throw new IncorrectInputException("Incorrect pin");}
        }else{throw new AccountNotFound("Account does not exist");}
        }



}
