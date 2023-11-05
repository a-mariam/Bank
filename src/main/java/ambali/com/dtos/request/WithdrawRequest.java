package ambali.com.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {
    private String pin;
    private String amount;
    private String accountNumber;
}
