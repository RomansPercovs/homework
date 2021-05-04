package homework.service.impl;

import homework.exception.CustomerNotFoundException;
import homework.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import homework.repositories.CustomerRepository;
import homework.service.CustomerService;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(Long id) {
        Optional<Customer> dbCustomer = customerRepository.findById(id);
        if(dbCustomer.isPresent()) {
            return dbCustomer.get();
        } else {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
    }

    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> dbCustomer = customerRepository.findById(id);
        if(dbCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }

    }

    @Override
    public void updateCustomer(Customer editedCustomer, Long id) {
        Optional<Customer> dbCustomer = customerRepository.findById(id);
        if(dbCustomer.isPresent()) {
            editedCustomer.setId(id);
            customerRepository.save(editedCustomer);
        } else {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
    }

}
