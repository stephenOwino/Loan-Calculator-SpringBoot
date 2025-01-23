package com.stephenowinoh.Loan_calculator;


import com.stephenowinoh.Loan_calculator.Controller.CustomerController;
import com.stephenowinoh.Loan_calculator.Controller.LoginRequest;
import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Service.ICustomerService;
import com.stephenowinoh.Loan_calculator.Jwt.JWTService;
import com.stephenowinoh.Loan_calculator.Jwt.JwtPayloadDTO;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

        private MockMvc mockMvc;

        @Mock
        private ICustomerService customerService;

        @Mock
        private JWTService jwtService;

        @InjectMocks
        private CustomerController customerController;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        }

        @Test
        public void testRegisterCustomer() throws Exception {
                CustomerDto customerDto = new CustomerDto("John", "Doe", "john_doe", "john@example.com", "password123", "password123");
                CustomerResponseDto responseDto = new CustomerResponseDto(1L, "John", "Doe", "john_doe", "john@example.com", "2025-01-24 12:00:00");

                when(customerService.registerCustomer(customerDto)).thenReturn(responseDto);

                mockMvc.perform(post("/api/customers/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"username\": \"john_doe\", \"email\": \"john@example.com\", \"password\": \"password123\", \"confirmPassword\": \"password123\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.username").value("john_doe"))
                        .andExpect(jsonPath("$.email").value("john@example.com"));
        }

        @Test
        public void testAuthenticateAndGetToken() throws Exception {
                LoginRequest loginRequest = new LoginRequest("john_doe", "password123");

                Customer customer = new Customer(1L, "John", "Doe", "john_doe", "john@example.com", "password123", null, null);
                JwtPayloadDTO payloadDTO = new JwtPayloadDTO(1L, "john_doe", "John", "Doe", null);
                String jwtToken = "fake-jwt-token";

                when(customerService.findByUsername("john_doe")).thenReturn(Optional.of(customer));
                when(jwtService.generateToken(payloadDTO)).thenReturn(jwtToken);

                mockMvc.perform(post("/api/customers/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\": \"john_doe\", \"password\": \"password123\"}"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(jwtToken));
        }

        @Test
        public void testGetCustomerById() throws Exception {
                CustomerResponseDto responseDto = new CustomerResponseDto(1L, "John", "Doe", "john_doe", "john@example.com", "2025-01-24 12:00:00");

                when(customerService.getCustomerDetails(1L)).thenReturn(Optional.of(responseDto));

                mockMvc.perform(get("/api/customers/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username").value("john_doe"));
        }

        @Test
        public void testUpdateCustomer() throws Exception {
                CustomerDto customerDto = new CustomerDto("John", "Doe", "john_doe", "john@example.com", "newpassword123", "newpassword123");
                CustomerResponseDto updatedResponse = new CustomerResponseDto(1L, "John", "Doe", "john_doe", "john@example.com", "2025-01-24 12:00:00");

                when(customerService.updateCustomer(1L, customerDto)).thenReturn(Optional.of(updatedResponse));

                mockMvc.perform(put("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"username\": \"john_doe\", \"email\": \"john@example.com\", \"password\": \"newpassword123\", \"confirmPassword\": \"newpassword123\"}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username").value("john_doe"));
        }

        @Test
        public void testDeleteCustomer() throws Exception {
                doNothing().when(customerService).deleteCustomer(1L);

                mockMvc.perform(delete("/api/customers/1"))
                        .andExpect(status().isNoContent());
        }

        @Test
        public void testGetAllCustomers() throws Exception {
                CustomerResponseDto customer1 = new CustomerResponseDto(1L, "John", "Doe", "john_doe", "john@example.com", "2025-01-24 12:00:00");
                CustomerResponseDto customer2 = new CustomerResponseDto(2L, "Jane", "Doe", "jane_doe", "jane@example.com", "2025-01-24 12:00:00");

                when(customerService.getAllCustomers()).thenReturn(List.of(customer1, customer2));

                mockMvc.perform(get("/api/customers"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].username").value("john_doe"))
                        .andExpect(jsonPath("$[1].username").value("jane_doe"));
        }
}
