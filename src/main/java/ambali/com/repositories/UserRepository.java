package ambali.com.repositories;


import ambali.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByPhoneNumber(String phoneNumber);
}
