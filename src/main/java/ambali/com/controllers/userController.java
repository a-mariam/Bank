package ambali.com.controllers;

import ambali.com.Exceptions.AccountNotFound;
import ambali.com.Exceptions.BankTransactionException;
import ambali.com.dtos.request.*;
import ambali.com.dtos.response.CreateUseResponse;
import ambali.com.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping()
@AllArgsConstructor
public class userController {

    private UserService userService;

    @PostMapping("/api/v1/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody CreateUser createUser){
        CreateUseResponse respone = null;
        try {
            respone = userService.createUse(createUser);
        } catch (BankTransactionException e) {
            return new ResponseEntity<>("account already exist", HttpStatus.ALREADY_REPORTED) ;
        }
        return ResponseEntity.status(CREATED).body(respone);
    }


    @PostMapping("/api/v1/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest depositRequest) {

        try {
            userService.deposit(depositRequest);
        } catch (AccountNotFound e) {
            throw new BankTransactionException(HttpStatus.CONFLICT, e.getMessage());
        }
        return null;
    }

    @PostMapping("/api/v1/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequest withdrawRequest){
        String response = null;
        try {
          response =   userService.withdraw(withdrawRequest);
        } catch (AccountNotFound e) {
            return new ResponseEntity<>("incorrect credentials", HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(CREATED).body(response);
    }

    @PostMapping("/api/v1/transfer")
    public void transfer(@RequestBody TransferRequest request){
        try {
            userService.transfer(request);
        } catch (AccountNotFound e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/v1/getBalance")
    public ResponseEntity<?> getBalance(@RequestBody GetBalanceRequest request){
        String response = null;
        try {
            response = userService.getBalance(request);
        } catch (AccountNotFound e) {
            return new ResponseEntity<>("incorrect credentials", HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(CREATED).body(response);
    }



}
