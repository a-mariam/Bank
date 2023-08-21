package ambali.com.services;

import ambali.com.Exceptions.AccountAlreadyExist;
import ambali.com.dtos.CreateAccountRequest;
import ambali.com.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.PanelUI;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class MinieAccountServiceTest {

    @Autowired
    private MinieAccountService minieAccountService;

    @Test
    public void test_Validate_phoneNumber_Method(){
        assertTrue(minieAccountService.validatePhoneNumber("08144063892"));
        assertTrue(minieAccountService.validatePhoneNumber("09144063892"));
        assertTrue(minieAccountService.validatePhoneNumber("09044063892"));
        assertTrue(minieAccountService.validatePhoneNumber("08044063892"));
        assertTrue(minieAccountService.validatePhoneNumber("07144063892"));
        assertTrue(minieAccountService.validatePhoneNumber("07044063892"));
        assertTrue(minieAccountService.validatePhoneNumber("234744063892"));
    }
    @Test
    public void test_Generate_AccountNumber(){
        String number = "07044063892";
        String expected = "7044063892";
        assertEquals(expected,minieAccountService.generateAccountNumber(number));
    }

   @Test
    public void testThat_Account_CanBe_Created(){
     CreateAccountRequest createAccountRequest = new CreateAccountRequest();
     createAccountRequest.setFirstName("Mariam");
     createAccountRequest.setLastName("Ambali");
     createAccountRequest.setSecondName("omolabake");
     createAccountRequest.setPhoneNumber("07044063892");
     createAccountRequest.setType("assets");
     createAccountRequest.setPin("1234");
     minieAccountService.createAccount(createAccountRequest);
     assertEquals(1L,minieAccountService.accountsCount() );
   }
    @Test
    public void testThat_Account_WillNot_BeCreated_IfPhoneNumber_AlreadyExist(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Mariam");
        createAccountRequest.setLastName("Ambali");
        createAccountRequest.setSecondName("omolabake");
        createAccountRequest.setPhoneNumber("07044063892");
        createAccountRequest.setType("assets");
        createAccountRequest.setPin("1228");
        minieAccountService.createAccount(createAccountRequest);
        assertThrows(AccountAlreadyExist.class, ()->minieAccountService.createAccount(createAccountRequest));
    }
    @Test
    public void testThat_Account_Can_Deposit(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setFirstName("Mimi");
        createAccountRequest.setType("liabilities");
        createAccountRequest.setLastName("mama");
        createAccountRequest.setSecondName("nini");
        createAccountRequest.setPhoneNumber("09044063892");
        createAccountRequest.setPin("2345");
       Account createdAccount = minieAccountService.createAccount(createAccountRequest);
       createdAccount.deposit(1000L);
       assertEquals(BigDecimal.valueOf(1000),createdAccount.getBalance("2345"));
    }
}