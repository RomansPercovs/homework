package homework.exception;

public class CustomerDebtNotFoundException extends RuntimeException{

    public CustomerDebtNotFoundException(String message) {
        super(message);
    }
}
