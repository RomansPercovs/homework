package homework.service;

import homework.exception.CustomerDebtNotFoundException;
import homework.repositories.CustomerDebtRepository;
import homework.service.impl.CustomerDebtServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static homework.TestHelper.createCustomerDebt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDebtServiceTest {

    @Mock
    private CustomerDebtRepository customerDebtRepository;

    @InjectMocks
    private CustomerDebtServiceImpl customerDebtService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deleteCustomerDebt_customerDebtNotFound_shouldThrowException() {
        exception.expect(CustomerDebtNotFoundException.class);
        exception.expectMessage("Debt with id 1 not found");
        when(customerDebtRepository.findById(1L)).thenReturn(Optional.empty());
        customerDebtService.deleteCustomerDebt(1L);
        verify(customerDebtRepository).findById(1L);
    }

    @Test
    public void updateCustomerDebt_customerDebtNotFound_shouldThrowException() {
        exception.expect(CustomerDebtNotFoundException.class);
        exception.expectMessage("Debt with id 1 not found");
        when(customerDebtRepository.findById(1L)).thenReturn(Optional.empty());
        customerDebtService.updateCustomerDebt(createCustomerDebt(),1L);
        verify(customerDebtRepository).findById(1L);
    }
}
