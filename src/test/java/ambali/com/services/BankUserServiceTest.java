package ambali.com.services;

import ambali.com.Exceptions.*;
import ambali.com.dtos.request.CreateUser;
import ambali.com.dtos.request.DepositRequest;
import ambali.com.dtos.request.TransferRequest;
import ambali.com.dtos.request.WithdrawRequest;
import ambali.com.dtos.response.CreateUseResponse;
import ambali.com.model.Account;
import ambali.com.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankUserServiceTest {

  @Autowired
  private BankUserService userService;
  @Autowired
  private AppAdminService appAdminService;

    @Test
    public void testThat_UserCanCreate_Account() throws UserAlreadyExist, InvalidPhoneNumberException, AccountAlreadyExist {

        CreateUser createUser = new CreateUser();
        createUser.setFirstName("firstname");
        createUser.setPin("3455");
        createUser.setEmail("first@gmail.com");
        createUser.setType("revenue");
        createUser.setPassword("21212");
        createUser.setLastName("lastname");
        createUser.setPhoneNumber("07012348950");
        createUser.setSecondName("secondName");
        createUser.setSecondPhoneNumber("09021390389");

        CreateUseResponse createdUser = userService.createUse(createUser);

        assertEquals(1L, userService.getUserCount());
        assertNotNull(createdUser);


    }

    @Test
    public void testThat_TwoUsers_CanCreate_Account() throws UserAlreadyExist, InvalidPhoneNumberException, AccountAlreadyExist {

        CreateUser createUser = new CreateUser();
        createUser.setFirstName("name");
        createUser.setPin("3455");
        createUser.setEmail("second@gmail.com");
        createUser.setType("revenue");
        createUser.setPassword("21212");
        createUser.setLastName("last");
        createUser.setPhoneNumber("08012348950");
        createUser.setSecondName("Name");
        createUser.setSecondPhoneNumber("09021390389");

        CreateUseResponse createdUser = userService.createUse(createUser);

        assertEquals(2L, userService.getUserCount());
        assertNotNull(createdUser);


        CreateUser createUser2 = new CreateUser();
        createUser2.setFirstName("secondne");
        createUser2.setPin("3355");
        createUser2.setEmail("first@gmail.com");
        createUser2.setType("revenue");
        createUser2.setPassword("23232");
        createUser2.setLastName("lastOne");
        createUser2.setPhoneNumber("09012348950");
        createUser2.setSecondName("secondName");
        createUser2.setSecondPhoneNumber("09021390389");

        CreateUseResponse createdUser2 = userService.createUse(createUser2);
        assertEquals(3L, userService.getUserCount());
        assertNotNull(createdUser2);


    }

    @Test
    public void test_thatUser_CanDeposit() throws IncorrectInputException, AccountNotFound {
        User user = appAdminService.findUserByPhoneNumber("09012348950");
        Account userAccount =  user.getAccount();

        DepositRequest request = new DepositRequest("2000",userAccount.getAccountNumber());
        userService.deposit(request);

        User user1 = appAdminService.findUserByPhoneNumber("09012348950");
        Account user1Account = user1.getAccount();
        assertEquals(2000, user1Account.getBalance().intValue());

    }

    @Test
    public void test_thatUser_CanWithdraw() throws IncorrectInputException, AccountNotFound {
        User user = appAdminService.findUserByPhoneNumber("09012348950");
        Account userAccount =  user.getAccount();
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber(userAccount.getAccountNumber());
        withdrawRequest.setAmount("1000");
        withdrawRequest.setPin(userAccount.getPin());

        userService.withdraw(withdrawRequest);

        User user1 = appAdminService.findUserByPhoneNumber("09012348950");
        Account user1Account = user1.getAccount();
        assertEquals(1000, user1Account.getBalance().intValue());

    }

    @Test
    public void testThat_UserCanNot_Deposit_WithWrong_Information(){
        User user = appAdminService.findUserByPhoneNumber("09012348950");
        Account userAccount =  user.getAccount();
        DepositRequest request = new DepositRequest("2000","393038393933");
        DepositRequest depositRequest = new DepositRequest("-2000",userAccount.getAccountNumber());

        assertThrows(AccountNotFound.class, ()-> userService.deposit(request));
        assertThrows(InvalidAmount.class, ()-> userService.deposit(depositRequest));

    }

    @Test
    public void testThat_UserCanNot_Withdraw_WithWrong_Information(){
        User user = appAdminService.findUserByPhoneNumber("09012348950");
        Account userAccount =  user.getAccount();
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountNumber("09012348950");
        withdrawRequest.setAmount("1000");
        withdrawRequest.setPin(userAccount.getPin());

        assertThrows(AccountNotFound.class, ()-> userService.withdraw(withdrawRequest));
        withdrawRequest.setAccountNumber(userAccount.getAccountNumber());
        withdrawRequest.setPin("3363");
        assertThrows(IncorrectInputException.class, ()-> userService.withdraw(withdrawRequest));
        withdrawRequest.setAmount("10000");
        assertThrows(IncorrectInputException.class, ()-> userService.withdraw(withdrawRequest));
        withdrawRequest.setPin(userAccount.getPin());
        withdrawRequest.setAmount("-21000");
        assertThrows(IncorrectInputException.class, ()-> userService.withdraw(withdrawRequest));
        withdrawRequest.setPin(userAccount.getPin());
        withdrawRequest.setAmount("-1000");
        assertThrows(IncorrectInputException.class, ()-> userService.withdraw(withdrawRequest));

    }

    @Test
    public void testThat_UserCanTransfer() throws IncorrectInputException, AccountNotFound {
        User sender = appAdminService.findUserByPhoneNumber("09012348950");
        User receiver = appAdminService.findUserByPhoneNumber("08012348950");
        Account senderAccount = sender.getAccount();
        Account receiverAccount = receiver.getAccount();
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount("1000");
        transferRequest.setReceiverAccountNumber(receiverAccount.getAccountNumber());
        transferRequest.setPin(senderAccount.getPin());
        transferRequest.setSenderAccountNumber(senderAccount.getAccountNumber());
        userService.transfer(transferRequest);

        User sender1 = appAdminService.findUserByPhoneNumber("09012348950");
        User receiver1 = appAdminService.findUserByPhoneNumber("08012348950");
        Account senderAccount1 = sender1.getAccount();
        Account receiverAccount1 = receiver1.getAccount();

        assertEquals(2000, senderAccount1.getBalance().intValue());
        assertEquals(16000, receiverAccount1.getBalance().intValue());
    }

    @Test
    public void test() throws InvalidPhoneNumberException, UserAlreadyExist {
        CreateUser createUser = new CreateUser();
        createUser.setFirstName("name23");
        createUser.setPin("3455");
        createUser.setEmail("second@gmail.com");
        createUser.setType("revenue");
        createUser.setPassword("21212");
        createUser.setLastName("last");
        createUser.setPhoneNumber("08012341234");
        createUser.setSecondName("Name");
        createUser.setSecondPhoneNumber("09021393389");

        CreateUseResponse createdUser = userService.createUse(createUser);


        CreateUser createUser2 = new CreateUser();
        createUser2.setFirstName("user");
        createUser2.setPin("3355");
        createUser2.setEmail("first@gmail.com");
        createUser2.setType("revenue");
        createUser2.setPassword("23232");
        createUser2.setLastName("userOne");
        createUser2.setPhoneNumber("09012345609");
        createUser2.setSecondName("secondName");
        createUser2.setSecondPhoneNumber("09021390389");
        CreateUseResponse user = userService.createUse(createUser2);

        assertNotNull(createdUser);
        assertNotNull(user);
    }

    @Test
    public void testThat_UserCan_Transfer() throws AccountNotFound, IncorrectInputException {
        User user1 = appAdminService.findUserByPhoneNumber("08012341234");
        User user2 = appAdminService.findUserByPhoneNumber("09012345609");

        Account sendAccount = user1.getAccount();
        Account  receiverAccount = user2.getAccount();

        DepositRequest request = new DepositRequest("4000", sendAccount.getAccountNumber());
        userService.deposit(request);

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderAccountNumber(sendAccount.getAccountNumber());
        transferRequest.setPin(sendAccount.getPin());
        transferRequest.setAmount("2000");
        transferRequest.setReceiverAccountNumber(receiverAccount.getAccountNumber());

        userService.transfer(transferRequest);

        User senderAfter = appAdminService.findUserByPhoneNumber("08012341234");
        User receiverAfter = appAdminService.findUserByPhoneNumber("09012345609");

        assertEquals(2000, senderAfter.getAccount().getBalance().intValue());
        assertEquals(2000, receiverAfter.getAccount().getBalance().intValue());
    }


}