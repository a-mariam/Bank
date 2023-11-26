package ambali.com.services;

import ambali.com.Exceptions.*;
import ambali.com.dtos.request.*;
import ambali.com.dtos.response.CreateUseResponse;
import ambali.com.model.Account;
import ambali.com.model.User;
import ambali.com.repositories.UserRepository;
import mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class BankUserService  implements UserService{


    private final AdminService adminService;

    private final UserRepository userRepository;
    private final MinieAccountService minieAccountService;

    public BankUserService(AdminService adminService, UserRepository userRepository, MinieAccountService minieAccountService) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.minieAccountService = minieAccountService;
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    @Override
    public CreateUseResponse     createUse(CreateUser createUser) throws UserAlreadyExist, InvalidPhoneNumberException {
     User user = adminService.createUser(createUser);
        return Mapper.map(user);
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) throws InvalidPhoneNumberException, AccountAlreadyExist {
        return  null;
    }

    @Override
    public String withdraw(WithdrawRequest withdrawRequest) throws IncorrectInputException, AccountNotFound {
      return   minieAccountService.withdraw(withdrawRequest);
    }

    @Override
    public void deposit(DepositRequest request) throws AccountNotFound {
        minieAccountService.deposit(request.getAccountNumber(), request.getAmount());
    }

    @Override
    public void transfer(TransferRequest transferRequest) throws IncorrectInputException, AccountNotFound {
        minieAccountService.transfer(transferRequest);
    }

    @Override
    public String getBalance(GetBalanceRequest request) throws AccountNotFound {
        Account foundAccount = minieAccountService.findAccount_ByAccountNumber(request.getAccountNumber());
        String balance = null;
        if (request.getPin().equals(foundAccount.getPin())) {
            balance = foundAccount.getBalance().toString();
            return balance;
        }
        throw new IncorrectInputException("Incorrect pin");
    }
}
