package ambali.com.Exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int statusCode;


    public ErrorResponse(int statusCode, String message){
        super();
        this.message = message;
    }
}
