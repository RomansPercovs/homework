package homework.controller;

import homework.exception.CustomerDebtNotFoundException;
import homework.exception.InvalidCustomerDebtException;
import homework.exception.InvalidInputException;
import homework.model.CustomerDebt;
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
import homework.service.CustomerDebtService;
import homework.util.InputValidator;

import java.util.List;

@RestController
@RequestMapping("debt")
public class CustomerDebtController {

    @Autowired
    private CustomerDebtService customerDebtService;
    @Autowired
    private InputValidator validator;

    private static final Logger logger = LoggerFactory.getLogger(CustomerDebtController.class);


    @GetMapping("/{customerId}}")
    public ResponseEntity<List<CustomerDebt>> getCustomerDebt(@PathVariable Long customerId) {
        ResponseEntity<List<CustomerDebt>> response;
        try {
            validator.validateId(customerId);
            List<CustomerDebt> customerDebtList = customerDebtService.getCustomerDebts(customerId);
            response = new ResponseEntity<>(customerDebtList, HttpStatus.OK);
        } catch (InvalidInputException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/new")
    public ResponseEntity<String> createCustomerDebt(@RequestBody CustomerDebt customerDebt) {
        ResponseEntity<String> response;
        try {
            validator.validateCustomerDebt(customerDebt);
            customerDebtService.createCustomerDebt(customerDebt);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidCustomerDebtException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteCustomerDebt(@PathVariable Long id) {
        ResponseEntity<String> response;
        try {
            validator.validateId(id);
            customerDebtService.deleteCustomerDebt(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerDebtNotFoundException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomerDebt(@RequestBody CustomerDebt customerDebt,
                                                     @PathVariable Long id) {
        ResponseEntity<String> response;
        try {
            validator.validateId(id);
            validator.validateCustomerDebt(customerDebt);
            customerDebtService.updateCustomerDebt(customerDebt, id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException | InvalidCustomerDebtException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerDebtNotFoundException e) {
            logger.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
