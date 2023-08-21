package ambali.com.model;

import ambali.com.Exceptions.IncorrectPinException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.math.BigDecimal;

@Entity
@Getter
@Setter


public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String accountNumber;
    private String firstName;
    private String secondName;
    private String lastName;
    private String phoneNumber;
    private Enum type;
    private BigDecimal balance = BigDecimal.ZERO;
    private String pin;


    public void deposit(long amount) {
     balance.add(BigDecimal.valueOf(amount));
        System.out.println("Balnce = " + balance);

    }
    public void setPin( String pin){
     String regex = "([0-9]{4})";
     if (pin.matches(regex)){
         this.pin = pin;
     }else throw new IllegalArgumentException("Please enter 4 length long numbers ");
    }
    @SneakyThrows
    public BigDecimal getBalance(String pin){
        if (pin.equals(this.pin)){
            return balance;
        }
         throw new IncorrectPinException("The pin entered is incorrect");
    }
}
