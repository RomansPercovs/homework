package homework.util;

import homework.exception.InvalidCustomerDebtException;
import homework.exception.InvalidCustomerException;
import homework.exception.InvalidInputException;
import homework.model.Customer;
import homework.model.CustomerDebt;
import homework.repositories.CustomerRepository;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class InputValidatorTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private InputValidator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void validateIdTest_shouldThrowInvalidInputException() {
        exception.expect(InvalidInputException.class);
        exception.expectMessage("Invalid id");
        validator.validateId(null);
    }

    @Test
    public void validateCustomer_withEmptyName_shouldThrowInvalidCustomerException() {
        exception.expect(InvalidCustomerException.class);
        exception.expectMessage("Invalid customer data");
        Customer customer = createCustomer();
        customer.setName("");
        validator.validateCustomer(customer);
    }

    @Test
    public void validateCustomer_withWrongEmail_shouldThrowInvalidCustomerException() {
        exception.expect(InvalidCustomerException.class);
        exception.expectMessage("Invalid customer data");
        Customer customer = createCustomer();
        customer.setEmail("wrong.email");
        validator.validateCustomer(customer);
    }

    @Test
    public void validateCustomerDebt_withWrongAmount_shouldThrowInvalidCustomerDebtException() {
        exception.expect(InvalidCustomerDebtException.class);
        exception.expectMessage("Invalid debt");
        CustomerDebt debt = createCustomerDebt();
        debt.setAmount(-2.00);
        validator.validateCustomerDebt(debt);
    }

    @Test
    public void validateCustomerDebt_withInvalidDate_shouldThrowInvalidCustomerDebtException() {
        exception.expect(InvalidCustomerDebtException.class);
        exception.expectMessage("Invalid debt");
        CustomerDebt debt = createCustomerDebt();
        debt.setDueDate(new DateTime(now()).minusDays(20));
        validator.validateCustomerDebt(debt);
    }

    @Test
    public void validateCustomerDebt_withNotExistingCustomer_shouldThrowInvalidCustomerDebtException() {
        exception.expect(InvalidCustomerDebtException.class);
        exception.expectMessage("Invalid debt");
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        CustomerDebt debt = createCustomerDebt();
        validator.validateCustomerDebt(debt);
        verify(customerRepository).findById(1L);
    }

    public static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setCountry("LV");
        customer.setPassword("password");
        customer.setEmail("test@test.lv");
        return customer;
    }

    public static CustomerDebt createCustomerDebt() {
        CustomerDebt debt = new CustomerDebt();
        debt.setId(1L);
        debt.setAmount(500.00);
        debt.setCurrency("EUR");
        debt.setDueDate(new DateTime(now()).plusYears(1));
        debt.setCustomer(createCustomer());
        return debt;
    }

}
