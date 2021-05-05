package homework.controller;

import homework.exception.CustomerNotFoundException;
import homework.exception.InvalidCustomerException;
import homework.exception.InvalidInputException;
import homework.model.Customer;
import homework.service.CustomerService;
import homework.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private InputValidator validator;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        ResponseEntity<Customer> response;
        try {
            validator.validateId(id);
            Customer customer = customerService.getCustomer(id);
            response = new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (InvalidInputException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    };

    @PostMapping(value = "/new")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        ResponseEntity<String> response;
        try {
            validator.validateCustomer(customer);
            customerService.createCustomer(customer);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidCustomerException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    };

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        ResponseEntity<String> response;
        try {
            validator.validateId(id);
            customerService.deleteCustomer(id);
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidInputException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        ResponseEntity<String> response;
        try {
            validator.validateId(id);
            validator.validateCustomer(customer);
            customerService.updateCustomer(customer, id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException | InvalidCustomerException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
