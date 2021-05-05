package homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import homework.model.Customer;
import homework.model.CustomerDebt;
import org.joda.time.DateTime;

import static org.joda.time.DateTime.now;

public class TestHelper {

    public static String asJsonString(Customer customer) {
        try {
            return new ObjectMapper().writeValueAsString(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(CustomerDebt debt) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            return mapper.writeValueAsString(debt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setCountry("LV");
        customer.setPassword("password");
        customer.setEmail("test@test.lv");
        return customer;
    }

    public static Customer createInvalidCustomer() {
        return new Customer();
    }

    public static Customer createCustomerWithInvalidEmail() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setCountry("LV");
        customer.setPassword("password");
        customer.setEmail("test.lv");
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

    public static CustomerDebt createCustomerDebtWithWrongAmount() {
        CustomerDebt debt = new CustomerDebt();
        debt.setId(1L);
        debt.setAmount(-2.00);
        debt.setCurrency("EUR");
        debt.setDueDate(new DateTime(now()).plusYears(1));
        debt.setCustomer(createCustomer());
        return debt;
    }

    public static CustomerDebt createCustomerDebtWithWrongDate() {
        CustomerDebt debt = new CustomerDebt();
        debt.setId(1L);
        debt.setAmount(500.00);
        debt.setCurrency("EUR");
        debt.setDueDate(new DateTime(now()).minusDays(20));
        debt.setCustomer(createCustomer());
        return debt;
    }
}
