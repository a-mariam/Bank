package ambali.com.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
@Test
    public void testThat_Account_Can_Deposit(){
    Account account = new Account();
    account.deposit(10000l);
    assertEquals(10000,account.getBalance());
}
}