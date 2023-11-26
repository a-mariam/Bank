package ambali.com.services;

import ambali.com.Exceptions.InvalidPhoneNumberException;
import ambali.com.Exceptions.UserAlreadyExist;
import ambali.com.dtos.request.CreateUser;
import ambali.com.model.Account;
import ambali.com.model.User;
import ambali.com.repositories.MinieAccountsRepository;
import ambali.com.repositories.UserRepository;
import lombok.AllArgsConstructor;
import mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppAdminService implements AdminService{

    private final MinieAccountsRepository minieAccountsRepository;
    private final UserRepository userRepository;



    @Override
    public Account findByAccountNumber(String accountNumber){
        return minieAccountsRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Account saveAccount(Account account) {

        return minieAccountsRepository.save(account);
    }

    @Override
    public Account findByAccountPhoneNumber(String phoneNumber) {

        return minieAccountsRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User createUser(CreateUser createUser) throws UserAlreadyExist, InvalidPhoneNumberException {
        System.out.println("exist == " + userRepository.findByPhoneNumber(createUser.getPhoneNumber()));
        if (userRepository.findByPhoneNumber(createUser.getPhoneNumber()) == null){
            User user = Mapper.map(createUser);
            Account account = Mapper.maps(createUser);
            minieAccountsRepository.save(account);
            user.setAccount(account);
            User savedUser = userRepository.save(user);
            System.out.println("saved ===> " + savedUser);
            return savedUser;
        }
        throw new UserAlreadyExist("User with this number already exist");
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public long accountsCount() {
        return minieAccountsRepository.count();
    }

}
