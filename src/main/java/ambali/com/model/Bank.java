package ambali.com.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Bank {
    private List<Account> accounts;
    private BigDecimal balance;
}
