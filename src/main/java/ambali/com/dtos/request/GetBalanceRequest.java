package ambali.com.dtos.request;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class GetBalanceRequest {
    private String accountNumber;
    private String pin;
}
