package homework.util;

import homework.exception.InvalidCustomerDebtException;
import homework.exception.InvalidInputException;
import homework.exception.InvalidCustomerException;
import homework.model.CustomerDebt;
import homework.model.Customer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import homework.repositories.CustomerRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputValidator {

    @Autowired
    private CustomerRepository customerRepository;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validateId(Long id) {
        if (id == null) {
            throw new InvalidInputException("Invalid id");
        }
    }

    public void validateCustomer(Customer customer) {
        if(nullOrEmptyField(customer.getName())
                || nullOrEmptyField(customer.getSurname())
                || nullOrEmptyField(customer.getCountry())
                || nullOrEmptyField(customer.getPassword())
                || !isValidEmail(customer.getEmail())) {
            throw new InvalidCustomerException("Invalid customer data");
        }
    }

    public void validateCustomerDebt(CustomerDebt debt) {
        if(!isValidCustomerDebt(debt)) {
            throw new InvalidCustomerDebtException("Invalid debt");
        }
    }

    private boolean isValidCustomerDebt(CustomerDebt customerDebt) {
        return customerDebt.getAmount() > 0
                && isValidDate(customerDebt.getDueDate())
                && isExistingCustomer(customerDebt.getCustomer().getId());
    }

    private boolean isValidDate(DateTime dueDate) {
        return dueDate.isAfterNow();
    }

    private boolean isExistingCustomer(Long userId) {
        return customerRepository.findById(userId).isPresent();
    }

    private boolean nullOrEmptyField(String field) {
        return field == null || field.isEmpty();
    }

    private boolean isValidEmail(String email) {
        if(nullOrEmptyField(email)) {
            return false;
        } else {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        }
    }
}
