package ambali.com.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUser {

    private String password;
    private String phoneNumber;
    private String secondPhoneNumber;
    private String firstName;
    private String lastName;
    private String secondName;
    private String type;
    private String pin;
    private String email;
}
