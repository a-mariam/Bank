package ambali.com;

import ambali.com.dtos.request.*;
import ambali.com.model.Account;
import ambali.com.model.User;
import ambali.com.services.AppAdminService;
import ambali.com.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
     private AppAdminService appAdminService;
    @Autowired
     private UserService userService;
    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testRegister(){

        CreateUser createUser = new CreateUser();
        createUser.setPhoneNumber("07055063892");
        createUser.setSecondName("mary");
        createUser.setType("assets");
        createUser.setPassword("11233");
        createUser.setEmail("marii@gmail.com");
        createUser.setPin("2332");
        createUser.setLastName("george");
        createUser.setFirstName("mimi");


        try {
            byte [] content = mapper.writeValueAsBytes(createUser);
            mockMvc.perform(post("/api/v1/createAccount")
                    .contentType(APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeposit(){

        User user = appAdminService.findUserByPhoneNumber("07055063892");
        Account account = user.getAccount();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber(account.getAccountNumber());
        depositRequest.setAmount("4000");
    try{
        byte [] content = mapper.writeValueAsBytes(depositRequest);
        mockMvc.perform(post("/api/v1/deposit")
                .content(content)
                .contentType(APPLICATION_JSON))
                .andDo(print());
    }catch (Exception e){
        e.printStackTrace();
    }
    User userAfter = appAdminService.findUserByPhoneNumber("07055063892");
    Account accountAfter = userAfter.getAccount();
    assertEquals(4000,accountAfter.getBalance().intValue());


    }


    @Test
    public void testTransfer(){
        CreateUser createUser = new CreateUser();
        createUser.setPhoneNumber("07055893892");
        createUser.setSecondName("jamie");
        createUser.setType("assets");
        createUser.setPassword("12233");
        createUser.setEmail("jamie@gmail.com");
        createUser.setPin("2332");
        createUser.setLastName("george");
        createUser.setFirstName("mimi");


        try {
            byte [] content = mapper.writeValueAsBytes(createUser);
            mockMvc.perform(post("/api/v1/createAccount")
                            .contentType(APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        User sender = appAdminService.findUserByPhoneNumber("07055063892");
        Account senderAccount = sender.getAccount();
        User receiver = appAdminService.findUserByPhoneNumber("07055893892");
        Account receiverAccount = receiver.getAccount();

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setReceiverAccountNumber(receiverAccount.getAccountNumber());
        transferRequest.setAmount("2000");
        transferRequest.setSenderAccountNumber(senderAccount.getAccountNumber());
        transferRequest.setPin(senderAccount.getPin());

        try {
            byte[] content = mapper.writeValueAsBytes(transferRequest);
            mockMvc.perform(post("/api/v1/transfer")
                    .content(content)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }

      User senderAfter = appAdminService.findUserByPhoneNumber("07055063892");
        User recceiverAfter = appAdminService.findUserByPhoneNumber("07055893892");
        assertEquals(2000, senderAfter.getAccount().getBalance().intValue());
        assertEquals(2000,recceiverAfter.getAccount().getBalance().intValue());
    }
    @Test
    public void testWithdraw() {
        User user = appAdminService.findUserByPhoneNumber("07055063892");
        Account userAccount = user.getAccount();

        WithdrawRequest request = new WithdrawRequest();
        request.setPin(userAccount.getPin());
        request.setAccountNumber(userAccount.getAccountNumber());
        request.setAmount("2000");

        try {
            byte[] content = mapper.writeValueAsBytes(request);
            mockMvc.perform(post("/api/v1/withdraw")
                            .contentType(APPLICATION_JSON)
                            .content(content))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
        User userAfterTransaction = appAdminService.findUserByPhoneNumber("07055063892");
        Account userAccountAfter = userAfterTransaction.getAccount();
        assertEquals(0, userAccountAfter.getBalance().intValue());
    }

    @Test
    public void testGetBalance(){
        User user = appAdminService.findUserByPhoneNumber("07055893892");
        Account userAccount = user.getAccount();
        GetBalanceRequest request = new GetBalanceRequest();
        request.setAccountNumber(userAccount.getAccountNumber());
        request.setPin(userAccount.getPin());

        try {
            byte [] content = mapper.writeValueAsBytes(request);
            mockMvc.perform(get("/api/v1/getBalance")
                    .contentType(APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
