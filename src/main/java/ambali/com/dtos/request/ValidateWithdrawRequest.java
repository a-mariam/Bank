package ambali.com.dtos.request;

import ambali.com.model.Account;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateWithdrawRequest {
    private Account senderAccount;
    private Account receiverAccount;
    private String amount;
    private String pin;

}
