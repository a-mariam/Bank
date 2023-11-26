package ambali.com.model;

import jakarta.persistence.Entity;


public enum AccountType {
   ASSETS("assets"),
   LIABILITIES("liabilties"),
   EQUITY("equity"),
   REVENUE("revenue"),
    EXPENSES("expenese");

    private final String value;

    AccountType(String value){this.value = value;}

    @Override
    public String toString() {
        return "AccountType{" +
                "value='" + value + '\'' +
                '}';
    }
}
