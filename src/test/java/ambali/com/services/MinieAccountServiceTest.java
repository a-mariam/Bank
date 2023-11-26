package ambali.com.services;

import ambali.com.BankTransactionApplication;
import ambali.com.Exceptions.*;
import ambali.com.dtos.request.CreateAccountRequest;
import ambali.com.dtos.request.TransferRequest;
import ambali.com.dtos.request.WithdrawRequest;
import ambali.com.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankTransactionApplication.class)
public class MinieAccountServiceTest {

    @Autowired
    private MinieAccountService minieAccountService;
    @Autowired
    private AppAdminService appAdminService;

   @Test
    public void testThat_AccountCanBe_Created() throws InvalidPhoneNumberException, AccountAlreadyExist {
       CreateAccountRequest request = new CreateAccountRequest();
       request.setSecondName("Omolabeke");
       request.setFirstName("Mariam");
       request.setLastName("Ambali");
       request.setPin("1111");
       request.setType("liabilities");
       request.setPhoneNumber("07044063892");
       request.setEmail("mariam@gamil.com") ;
       Account account = minieAccountService.createAccount(request);
       assertNotNull(account);
       assertNotNull(account.getAccountNumber());
   }
    @Test
    public void testThat_ExceptionIs_thrownWHen_User_TryTo_CreateAccount_WithIncompleteInfo() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("Omolabeke");
        request.setFirstName("Mariam");
        request.setLastName("Ambali");
        request.setPin("1111");
        request.setType("liabilities");
        request.setEmail("mariam@gamil.com") ;
        assertThrows(NullPointerException .class, () ->minieAccountService.createAccount(request));
    }
    @Test
    public void testThat_AccountCan_Deposit() throws InvalidPhoneNumberException, AccountAlreadyExist, AccountNotFound {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("labake");
        request.setFirstName("nyx");
        request.setLastName("bal");
        request.setPin("2222");
        request.setType("liabilities");
        request.setPhoneNumber("07044063894");
        request.setEmail("nyx@gamil.com") ;
        Account account = minieAccountService.createAccount(request);
        minieAccountService.deposit(account.getAccountNumber(),"2000");
        Account foundAccount = appAdminService.findByAccountNumber(account.getAccountNumber());
        assertEquals(2000.00,foundAccount.getBalance().doubleValue());
    }

    @Test
    public void testThat_Exception_IsThrown_When_Deposit_OfAmount_LessThan_ZeroIsAttempt() throws RuntimeException, InvalidPhoneNumberException, AccountAlreadyExist {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("labake1");
        request.setFirstName("nyx1");
        request.setLastName("bal1");
        request.setPin("2222");
        request.setType("liabilities");
        request.setPhoneNumber("07055063894");
        request.setEmail("nyx@gamil.com") ;
        Account account = minieAccountService.createAccount(request);

        assertThrows(InvalidAmount.class, ()-> minieAccountService.deposit(account.getAccountNumber(),"0"));

    }
    @Test
    public void testThat_UserCan_Withdrawal(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("treasure");
        request.setFirstName("darari");
        request.setLastName("bal1");
        request.setPin("2222");
        request.setType("liabilities");
        request.setPhoneNumber("07059863894");
        request.setEmail("nyx@gamil.com") ;
        Account account = null;
        try {
             account = minieAccountService.createAccount(request);
            minieAccountService.deposit(account.getAccountNumber(), "2000");
        } catch (InvalidPhoneNumberException | AccountAlreadyExist | AccountNotFound e) {
            throw new RuntimeException(e);
        }
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAmount("1000");
        withdrawRequest.setAccountNumber(account.getAccountNumber());
        withdrawRequest.setPin(account.getPin());

        System.out.println("after depositing "+ account);
        try {
            minieAccountService.withdraw(withdrawRequest);
        } catch (IncorrectInputException | AccountNotFound e) {
            throw new RuntimeException(e);
        }
        Account foundAccount = null;
        try {
            foundAccount = minieAccountService.findAccount_ByAccountNumber(account.getAccountNumber());
        } catch (AccountNotFound e) {
            throw new RuntimeException(e);
        }
        System.out.println("after withdrawal = "+ foundAccount);
        assertEquals(1000.00,foundAccount.getBalance().doubleValue());

    }
    @Test
    public void testThat_ExceptionIs_ThrownWhen_UserAttempt_ToWithdraw_WithIncorrectPin_OrInvalidAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("love");
        request.setFirstName("lost");
        request.setLastName("giveon");
        request.setPin("2222");
        request.setType("liabilities");
        request.setPhoneNumber("09059863894");
        request.setEmail("giveon@gamil.com");
        Account account = null;
        try {
            account = minieAccountService.createAccount(request);
            minieAccountService.deposit(account.getAccountNumber(), "2000");
        } catch (InvalidPhoneNumberException | AccountAlreadyExist | AccountNotFound e)  {
            throw new RuntimeException(e);
        }
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAmount("9000");
        withdrawRequest.setAccountNumber(account.getAccountNumber());
        withdrawRequest.setPin(account.getPin());

        assertThrows(IncorrectInputException.class, ()-> minieAccountService.withdraw(withdrawRequest));
        withdrawRequest.setPin("9989");
        assertThrows(IncorrectInputException.class, ()-> minieAccountService.withdraw(withdrawRequest));
        withdrawRequest.setAccountNumber("8977900--");
        assertThrows(AccountNotFound.class, ()-> minieAccountService.withdraw(withdrawRequest));

    }
    @Test
    public void testUser_CanUser_Transfer(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("sza");
        request.setFirstName("kendrick");
        request.setLastName("giveon");
        request.setPin("2222");
        request.setType("liabilities");
        request.setPhoneNumber("09059863894");
        request.setEmail("sza@gamil.com");

        CreateAccountRequest request1 = new CreateAccountRequest();
        request1.setSecondName("sza2");
        request1.setFirstName("kendrick2");
        request1.setLastName("giveon2");
        request1.setPin("2222");
        request1.setType("liabilities");
        request1.setPhoneNumber("09059092894");
        request1.setEmail("sza2@gamil.com");

        Account account = null;
        Account account2 = null;

        try {
            account = minieAccountService.createAccount(request);
            account2 = minieAccountService.createAccount(request1);
            minieAccountService.deposit(account.getAccountNumber(), "2000");
        } catch (InvalidPhoneNumberException | AccountAlreadyExist | AccountNotFound e)  {
            throw new RuntimeException(e);
        }

        Account acc1 = null;
        Account acc2 = null;
        try {
            acc1 = minieAccountService.findAccount_ByAccountNumber(account.getAccountNumber());
            acc2 = minieAccountService.findAccount_ByAccountNumber(account2.getAccountNumber());
        } catch (AccountNotFound e) {
            throw new RuntimeException(e);
        }
        System.out.println("after depositing sender account = "+ acc1);
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderAccountNumber(acc1.getAccountNumber());
        transferRequest.setReceiverAccountNumber(acc2.getAccountNumber());
        transferRequest.setAmount("1000");
        transferRequest.setPin(acc1.getPin());

        try {
            minieAccountService.transfer(transferRequest);
        } catch (AccountNotFound | IncorrectInputException e) {
            throw new RuntimeException(e);
        }

        Account sender = null;
        Account receiver = null;
        try {
             sender = minieAccountService.findAccount_ByAccountNumber(account.getAccountNumber());
             receiver = minieAccountService.findAccount_ByAccountNumber(account2.getAccountNumber());
        } catch (AccountNotFound e) {
            throw new RuntimeException(e);
        }
        System.out.println("sender after == " + sender.getBalance());
        System.out.println("receiver after == "+ receiver.getBalance());

        assertThat(sender.getBalance().intValue()).isEqualTo(1000);
        assertThat(receiver.getBalance().intValue()).isEqualTo(1000);

    }

    @Test
    public void testThat_ExceptionIs_ThrownWhen_UserTryTo_Transfer_WithWrongINfo(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setSecondName("coco");
        request.setFirstName("jones");
        request.setLastName("dodo");
        request.setPin("2342");
        request.setType("assets");
        request.setPhoneNumber("09059863904");
        request.setEmail("coco@gamil.com");

        CreateAccountRequest request1 = new CreateAccountRequest();
        request1.setSecondName("lie");
        request1.setFirstName("liu");
        request1.setLastName("lexie");
        request1.setPin("0022");
        request1.setType("equity");
        request1.setPhoneNumber("09055993489");
        request1.setEmail("lixie2@gamil.com");

        Account account = null;
        Account account2 = null;

        try {
            account = minieAccountService.createAccount(request);
            account2 = minieAccountService.createAccount(request1);
            minieAccountService.deposit(account.getAccountNumber(), "2000");
        } catch (InvalidPhoneNumberException | AccountAlreadyExist | AccountNotFound e)  {
            System.out.println(e.getMessage());
        }

        Account acc1 = null;
        Account acc2 = null;
        try {
            assert account != null;
            acc1 = minieAccountService.findAccount_ByAccountNumber(account.getAccountNumber());
            acc2 = minieAccountService.findAccount_ByAccountNumber(account2.getAccountNumber());
        } catch (AccountNotFound e) {
            throw new RuntimeException(e);
        }
        System.out.println("after depositing sender account = "+ acc1);
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderAccountNumber(acc1.getAccountNumber());
        transferRequest.setReceiverAccountNumber(acc2.getAccountNumber());
        transferRequest.setAmount("1000");
        transferRequest.setPin(acc1.getPin());

//      assertThrows(AccountNotFound.class, () -> minieAccountService. transfer(transferRequest));
      transferRequest.setPin("938h");
      assertThrows(IncorrectInputException.class, ()->minieAccountService.transfer(transferRequest));
      transferRequest.setPin(acc1.getPin());
      transferRequest.setReceiverAccountNumber("uii9trc");
      assertThrows(AccountNotFound.class, ()-> minieAccountService.transfer(transferRequest));
      transferRequest.setReceiverAccountNumber(acc2.getAccountNumber());
      transferRequest.setAmount("3000");
      assertThrows(InsufficientException.class, ()-> minieAccountService.transfer(transferRequest));
//      transferRequest.setAmount("1000");
      transferRequest.setAmount("-293");
      assertThrows(InvalidAmount.class, ()-> minieAccountService.transfer(transferRequest));

    }




    }