package homework.controller;

import homework.ApplicationMain;
import homework.model.CustomerDebt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static homework.TestHelper.asJsonString;
import static homework.TestHelper.createCustomerDebt;
import static homework.TestHelper.createCustomerDebtWithWrongAmount;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class CustomerDebtControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void customerDebtControllerTest() throws Exception {
        CustomerDebt debt = createCustomerDebt();
        debt.getCustomer().setId(1L);
        CustomerDebt editedCustomerDebt = createCustomerDebt();
        editedCustomerDebt.setAmount(5.00);
        CustomerDebt invalidDebt = createCustomerDebtWithWrongAmount();

        // Get debt from not existing customer
        mockMvc.perform(get("/debt/{customerId}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        // Create new debt
        mockMvc.perform(post("/debt/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isCreated());

        // Create invalid debt
        mockMvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidDebt)))
                .andExpect(status().isBadRequest());

        // Get debt from existing customer

        // Edit debt with invalid data
        mockMvc.perform(put("/debt/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidDebt)))
                .andExpect(status().isBadRequest());

        // Edit not existing debt
        mockMvc.perform(put("/debt/update/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(debt)))
                .andExpect(status().isNotFound());

        // Edit existing debt

        // Delete not existing debt
        mockMvc.perform(delete("/debt/remove/{id}", 2))
                .andExpect(status().isNotFound());

        // Delete existing debt
        // TODO
    }
}
