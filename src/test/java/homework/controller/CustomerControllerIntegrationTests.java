package homework.controller;

import com.jayway.jsonpath.JsonPath;
import homework.ApplicationMain;
import homework.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static homework.TestHelper.asJsonString;
import static homework.TestHelper.createCustomer;
import static homework.TestHelper.createInvalidCustomer;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class CustomerControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void customerControllerTest() throws Exception {
        Customer customer = createCustomer();
        Customer editedCustomer = createCustomer();
        editedCustomer.setName("Billy");
        Customer invalidCustomer = createInvalidCustomer();

        // Get not existing customer
        mockMvc.perform(get("/customer/{id}", 2))
                .andExpect(status().isNotFound());

        // Get existing customer
        MvcResult existingCustomerResponse = mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        Integer id = JsonPath.read(existingCustomerResponse.getResponse().getContentAsString(), "$.id");
        assertEquals(new Integer(1), id);

        // Create new customer
        mockMvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated());

        // Create invalid customer
        mockMvc.perform(post("/customer/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidCustomer)))
                .andExpect(status().isBadRequest());

        // Edit customer with invalid data
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidCustomer)))
                .andExpect(status().isBadRequest());

        // Edit not existing customer
        mockMvc.perform(put("/customer/edit/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isNotFound());

        // Edit existing customer
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(editedCustomer)))
                .andExpect(status().isOk());
        MvcResult editedExistingCustomerResponse = mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        String name = JsonPath.read(editedExistingCustomerResponse.getResponse().getContentAsString(), "$.name");
        assertEquals("Billy", name);
        mockMvc.perform(put("/customer/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk());

        // Delete not existing customer
        mockMvc.perform(delete("/customer/delete/{id}", 2))
                .andExpect(status().isNotFound());

        // Delete existing customer
        // TODO
    }
}
