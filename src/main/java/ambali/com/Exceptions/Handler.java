package ambali.com.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(value = BankTransactionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBankTransactionException(BankTransactionException exception){
        return new ErrorResponse(HttpStatus.CONFLICT.value(),
                exception.getMessage());
    }
}
