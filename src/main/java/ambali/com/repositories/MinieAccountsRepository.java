package ambali.com.repositories;

import ambali.com.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinieAccountsRepository extends JpaRepository<Account, String> {
  Account findByPhoneNumber(String phoneNumber);
}
