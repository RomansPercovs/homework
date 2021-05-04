package homework.service;

import homework.exception.CustomerNotFoundException;
import homework.model.Customer;
import homework.repositories.CustomerRepository;
import homework.service.impl.CustomerServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static homework.util.InputValidatorTest.createCustomer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getCustomerTest_shouldReturnCustomer() {
        Customer customer = createCustomer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer actualCustomer = customerService.getCustomer(1L);
        assertEquals(actualCustomer.getName(), customer.getName());
        verify(customerRepository).findById(1L);
    }

    @Test
    public void getCustomerTest_customerNotFound_shouldThrowException() {
        exception.expect(CustomerNotFoundException.class);
        exception.expectMessage("Customer with id 1 not found");
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        customerService.getCustomer(1L);
        verify(customerRepository).findById(1L);
    }

    @Test
    public void deleteCustomerTest_customerNotFound_shouldThrowException() {
        exception.expect(CustomerNotFoundException.class);
        exception.expectMessage("Customer with id 1 not found");
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        customerService.deleteCustomer(1L);
        verify(customerRepository).findById(1L);
    }

    @Test
    public void updateCustomerTest_customerNotFound_shouldThrowException() {
        exception.expect(CustomerNotFoundException.class);
        exception.expectMessage("Customer with id 1 not found");
        Customer customer = createCustomer();
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        customerService.updateCustomer(customer, 1L);
        verify(customerRepository).findById(1L);
    }
}
