package ambali.com.MinieRepositoriesTest;

import ambali.com.model.Account;
import ambali.com.repositories.MinieAccountsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MinieAccountsRepositoryTest {
    @Autowired
    private MinieAccountsRepository minieAccountsRepository;
    @Test
    public void testThat_Account_CanBe_Created(){
        Account account = new Account();
        minieAccountsRepository.save(account);
        assertEquals(1L, minieAccountsRepository.count());

    }
    @Test
    public void testFindBy(){

    }
}
