package homework.util;

import homework.exception.InvalidCustomerDebtException;
import homework.exception.InvalidCustomerException;
import homework.exception.InvalidInputException;
import homework.model.Customer;
import homework.model.CustomerDebt;
import homework.repositories.CustomerRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static homework.TestHelper.createCustomerDebt;
import static homework.TestHelper.createCustomerDebtWithWrongAmount;
import static homework.TestHelper.createCustomerDebtWithWrongDate;
import static homework.TestHelper.createCustomerWithInvalidEmail;
import static homework.TestHelper.createInvalidCustomer;
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
    public void validateInvalidCustomer_shouldThrowInvalidCustomerException() {
        exception.expect(InvalidCustomerException.class);
        exception.expectMessage("Invalid customer data");
        Customer customer = createInvalidCustomer();
        validator.validateCustomer(customer);
    }

    @Test
    public void validateInvalidCustomer_withWrongEmail_shouldThrowInvalidCustomerException() {
        exception.expect(InvalidCustomerException.class);
        exception.expectMessage("Invalid customer data");
        Customer customer = createCustomerWithInvalidEmail();
        validator.validateCustomer(customer);
    }

    @Test
    public void validateCustomerDebt_withWrongAmount_shouldThrowInvalidCustomerDebtException() {
        exception.expect(InvalidCustomerDebtException.class);
        exception.expectMessage("Invalid debt");
        CustomerDebt debt = createCustomerDebtWithWrongAmount();
        validator.validateCustomerDebt(debt);
    }

    @Test
    public void validateCustomerDebt_withInvalidDate_shouldThrowInvalidCustomerDebtException() {
        exception.expect(InvalidCustomerDebtException.class);
        exception.expectMessage("Invalid debt");
        CustomerDebt debt = createCustomerDebtWithWrongDate();
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



}
