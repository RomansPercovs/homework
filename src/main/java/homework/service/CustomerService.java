package homework.service;

import homework.model.Customer;

public interface CustomerService {
    Customer getCustomer(Long id);
    void createCustomer(Customer customer);
    void deleteCustomer(Long id);
    void updateCustomer(Customer customer, Long id);
}
