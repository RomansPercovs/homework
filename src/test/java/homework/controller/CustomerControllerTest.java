package homework.controller;

import homework.exception.CustomerNotFoundException;
import homework.exception.InvalidCustomerException;
import homework.exception.InvalidInputException;
import homework.model.Customer;
import homework.service.CustomerService;
import homework.util.InputValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static homework.TestHelper.asJsonString;
import static homework.TestHelper.createCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private InputValidator validator;

    @Test
    public void getCustomerTest_returnOK() throws Exception {
        Customer customer = createCustomer();
        when(customerService.getCustomer(1L)).thenReturn(customer);
        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerWithInvalidIdTest_returnBadRequest() throws Exception {
        doThrow(new InvalidInputException("Invalid id")).when(validator).validateId(1L);
        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getNotExistingCustomerTest_returnNotFound() throws Exception {
        doThrow(new CustomerNotFoundException("Customer with id 1 not found"))
                .when(customerService).getCustomer(1L);
        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCustomerTest_returnCreated() throws Exception {
        Customer customer = createCustomer();
        mockMvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createInvalidCustomerTest_returnBadRequest() throws Exception {
        Customer customer = createCustomer();
        doThrow(new InvalidCustomerException("Invalid customer data"))
                .when(validator).validateCustomer(any(Customer.class));
        mockMvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCustomerTest_returnNoContent() throws Exception {
        mockMvc.perform(delete("/customer/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotExistingCustomerTest_returnNotFound() throws Exception {
        doThrow(new CustomerNotFoundException("Customer with id 1 not found"))
                .when(customerService).deleteCustomer(1L);
        mockMvc.perform(delete("/customer/delete/{id}", 1))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCustomerWithInvalidId_returnBadRequest() throws Exception {
        doThrow(new InvalidInputException("Invalid id"))
                .when(validator).validateId(1L);
        mockMvc.perform(delete("/customer/delete/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomer_returnOk() throws Exception {
        Customer customer = createCustomer();
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCustomerWithInvalidId_returnBadRequest() throws Exception {
        Customer customer = createCustomer();
        doThrow(new InvalidInputException("Invalid id"))
                .when(validator).validateId(1L);
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerWithInvalidData_returnBadRequest() throws Exception {
        Customer customer = createCustomer();
        doThrow(new InvalidCustomerException("Invalid customer data"))
                .when(validator).validateCustomer(any(Customer.class));
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNotExistingCustomer_returnNotFound() throws Exception {
        Customer customer = createCustomer();
        doThrow(new CustomerNotFoundException("Customer with id 1 not found"))
                .when(customerService).updateCustomer(any(Customer.class), anyLong());
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isNotFound());
    }
}
