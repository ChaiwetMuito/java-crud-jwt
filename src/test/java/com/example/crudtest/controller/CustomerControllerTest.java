package com.example.crudtest.controller;

import com.example.crudtest.entity.Customer;
import com.example.crudtest.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id(1L)
                .firstName("Chaiwet")
                .lastName("Muito")
                .email("chaiwet.muito@gmail.com")
                .build();
    }

    @Test
    @WithMockUser
    public void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Chaiwet"))
                .andExpect(jsonPath("$[0].lastName").value("Muito"))
                .andExpect(jsonPath("$[0].email").value("chaiwet.muito@gmail.com"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @WithMockUser
    public void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Chaiwet"))
                .andExpect(jsonPath("$.lastName").value("Muito"))
                .andExpect(jsonPath("$.email").value("chaiwet.muito@gmail.com"));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    @WithMockUser
    public void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName").value("Chaiwet"))
                .andExpect(jsonPath("$.lastName").value("Muito"))
                .andExpect(jsonPath("$.email").value("chaiwet.muito@gmail.com"));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    @WithMockUser
    public void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Chaiwet"))
                .andExpect(jsonPath("$.lastName").value("Muito"))
                .andExpect(jsonPath("$.email").value("chaiwet.muito@gmail.com"));

        verify(customerService, times(1)).updateCustomer(eq(1L), any(Customer.class));
    }

    @ParameterizedTest
    @WithMockUser
    @CsvSource({
            "1, true, 200",
            "1, false, 204"
    })
    public void testDeleteCustomer(Long customerId, boolean serviceResult, int expectedStatus) throws Exception {
        when(customerService.deleteCustomer(customerId)).thenReturn(serviceResult);

        mockMvc.perform(delete("/api/customers/" + customerId))
                .andExpect(status().is(expectedStatus));

        verify(customerService, times(1)).deleteCustomer(customerId);
    }


}
