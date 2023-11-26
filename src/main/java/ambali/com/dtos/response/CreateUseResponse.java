package ambali.com.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUseResponse {

    private String accountNumber;
    private String firstName;
    private String secondName;
    private String lastName;
    private String accountType;
    private String phoneNumber;
    private String email;
    private String balance;
}
