package ambali.com.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private String pin;
    private  String receiverAccountNumber;
    private String amount;
    private String senderAccountNumber;


}
