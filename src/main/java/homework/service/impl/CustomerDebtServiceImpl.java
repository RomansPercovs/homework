package homework.service.impl;

import homework.exception.CustomerDebtNotFoundException;
import homework.model.CustomerDebt;
import homework.service.CustomerDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import homework.repositories.CustomerDebtRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDebtServiceImpl implements CustomerDebtService {

    @Autowired
    private CustomerDebtRepository customerDebtRepository;

    @Override
    public List<CustomerDebt> getCustomerDebts(Long customerId){
        return customerDebtRepository.findAllByCustomerId(customerId);
    }

    @Override
    public void createCustomerDebt(CustomerDebt customerDebt) {
        customerDebtRepository.save(customerDebt);
    }

    @Override
    public void deleteCustomerDebt(Long id) {
        Optional<CustomerDebt> dbCustomerDebt = customerDebtRepository.findById(id);
        if (dbCustomerDebt.isPresent()) {
            customerDebtRepository.deleteById(id);
        } else {
            throw new CustomerDebtNotFoundException("Debt with id " + id + " not found");
        }
    }

    @Override
    public void updateCustomerDebt(CustomerDebt editedCustomerDebt, Long id) {
        Optional<CustomerDebt> dbCustomerDebt = customerDebtRepository.findById(id);
        if (dbCustomerDebt.isPresent()) {
            editedCustomerDebt.setId(id);
            customerDebtRepository.save(editedCustomerDebt);
        } else {
            throw new CustomerDebtNotFoundException("Debt with id " + id + " not found");
        }
    }

}
