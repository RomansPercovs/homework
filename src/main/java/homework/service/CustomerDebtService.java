package homework.service;

import homework.model.CustomerDebt;

import java.util.List;

public interface CustomerDebtService {
    List<CustomerDebt> getCustomerDebts(Long customerId);
    void createCustomerDebt(CustomerDebt customerDebt);
    void deleteCustomerDebt(Long id);
    void updateCustomerDebt(CustomerDebt customerDebt, Long id);
}
