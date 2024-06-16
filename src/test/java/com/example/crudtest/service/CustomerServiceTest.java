package com.example.crudtest.service;

import com.example.crudtest.entity.Customer;
import com.example.crudtest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

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
    public void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(1L);
        assertTrue(result.isPresent());
        assertEquals("Chaiwet", result.get().getFirstName());
        assertEquals("Muito", result.get().getLastName());
        assertEquals("chaiwet.muito@gmail.com", result.get().getEmail());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);
        assertEquals("Chaiwet", result.getFirstName());
        assertEquals("Muito", result.getLastName());
        assertEquals("chaiwet.muito@gmail.com", result.getEmail());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        customer.setFirstName("Jane");
        Optional<Customer> result = customerService.updateCustomer(1L, customer);
        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
        assertEquals("Muito", result.get().getLastName());
        assertEquals("chaiwet.muito@gmail.com", result.get().getEmail());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        boolean result = customerService.deleteCustomer(1L);
        assertTrue(result);
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).delete(customer);
    }
}
