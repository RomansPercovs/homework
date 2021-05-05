package homework.controller;

import homework.exception.CustomerDebtNotFoundException;
import homework.exception.InvalidCustomerDebtException;
import homework.exception.InvalidInputException;
import homework.model.CustomerDebt;
import homework.service.CustomerDebtService;
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
import static homework.TestHelper.createCustomerDebt;
import static java.util.Collections.singletonList;
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
@WebMvcTest(CustomerDebtController.class)
public class CustomerDebtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerDebtService customerDebtService;

    @MockBean
    private InputValidator validator;

    @Test
    public void getCustomerDebtTest_returnOK() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        when(customerDebtService.getCustomerDebts(1L)).thenReturn(singletonList(debt));
        mockMvc.perform(get("/debt/{customerId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerDebtWithInvalidIdTest_returnBadRequest() throws Exception {
        doThrow(new InvalidInputException("Invalid id")).when(validator).validateId(1L);
        mockMvc.perform(get("/debt/{customerId}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCustomerDebtTest_returnCreated() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        mockMvc.perform(post("/debt/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createInvalidCustomerDebtTest_returnBadRequest() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        doThrow(new InvalidCustomerDebtException("Invalid debt"))
                .when(validator).validateCustomerDebt(any(CustomerDebt.class));
        mockMvc.perform(post("/debt/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCustomerDebtTest_returnNoContent() throws Exception {
        mockMvc.perform(delete("/debt/remove/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotExistingCustomerDebtTest_returnNotFound() throws Exception {
        doThrow(new CustomerDebtNotFoundException("Debt with id 1 not found"))
                .when(customerDebtService).deleteCustomerDebt(1L);
        mockMvc.perform(delete("/debt/remove/{id}", 1))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCustomerDebtWithInvalidId_returnBadRequest() throws Exception {
        doThrow(new InvalidInputException("Invalid id"))
                .when(validator).validateId(1L);
        mockMvc.perform(delete("/debt/remove/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerDebt_returnOk() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        mockMvc.perform(put("/debt/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCustomerDebtWithInvalidId_returnBadRequest() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        doThrow(new InvalidInputException("Invalid id"))
                .when(validator).validateId(1L);
        mockMvc.perform(put("/debt/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerDebtWithInvalidData_returnBadRequest() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        doThrow(new InvalidCustomerDebtException("Invalid debt"))
                .when(validator).validateCustomerDebt(any(CustomerDebt.class));
        mockMvc.perform(put("/debt/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNotExistingCustomerDebt_returnNotFound() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        doThrow(new CustomerDebtNotFoundException("Debt with id 1 not found"))
                .when(customerDebtService).updateCustomerDebt(any(CustomerDebt.class), anyLong());
        mockMvc.perform(put("/debt/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isNotFound());
    }

}
