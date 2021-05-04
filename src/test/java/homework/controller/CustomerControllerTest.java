package homework.controller;

import homework.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import homework.service.CustomerService;
import homework.util.InputValidator;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

//    @Test
//    public void getCustomerTest_returnOK() throws Exception {
//        mockMvc.perform(get("/customer/{id}", 1)).andExpect(status().isOk());
//    }
//
//    @Test
//    public void getCustomerWithInvalidIdTest_returnBadRequest() throws Exception {
//        doThrow(new InvalidInputException("Invalid id")).when(validator).validateId(1L);
//        mockMvc.perform(get("/customer/{id}", 1)).andExpect(status().isBadRequest());
//    }

    @Test
    public void getNotExistingCustomerTest_returnNotFound(){}

    @Test
    public void createCustomerTest_returnCreated(){}

    @Test
    public void createInvalidCustomerTest_returnBadRequest(){}

    @Test
    public void deleteCustomerTest_returnNoContent(){}

    @Test
    public void deleteNotExistingCustomerTest_returnNotFound(){}

    @Test
    public void deleteCustomerWithInvalidId_returnBadRequest(){}

    @Test
    public void updateCustomer_returnOk(){}

    @Test
    public void updateCustomerWithInvalidId_returnBadRequest(){}

    @Test
    public void updateCustomerWithInvalidData_returnBadRequest(){}

    @Test
    public void updateNotExistingCustomer_returnNotFound(){}
}
