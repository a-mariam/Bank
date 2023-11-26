package ambali.com.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advicer {


        @ExceptionHandler(value = BankTransactionException.class)
        public ResponseEntity<?> exception(BankTransactionException exception) {
            return new ResponseEntity<>("Account already exist", HttpStatus.NOT_FOUND);
        }

}
