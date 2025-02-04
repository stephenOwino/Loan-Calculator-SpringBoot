package com.stephenowinoh.Loan_calculator;

import com.stephenowinoh.Loan_calculator.Controller.CustomerController;
import com.stephenowinoh.Loan_calculator.Controller.LoginRequest;
import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Exception.BadRequestException;
import com.stephenowinoh.Loan_calculator.Jwt.JWTService;
import com.stephenowinoh.Loan_calculator.Role.Role;
import com.stephenowinoh.Loan_calculator.Service.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

        @Mock
        private ICustomerService customerService;

        @Mock
        private AuthenticationManager authenticationManager;

        @Mock
        private JWTService jwtService;

        @InjectMocks
        private CustomerController customerController;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        void testRegisterCustomer() {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setUsername("john_doe");
                customerDto.setPassword("password");
                customerDto.setFirstName("John");
                customerDto.setLastName("Doe");

                CustomerResponseDto customerResponseDto = new CustomerResponseDto(
                        1L,  // id
                        "John",  // firstName
                        "Doe",  // lastName
                        "john_doe",  // username
                        "john.doe@example.com",  // email
                        "2025-02-04T09:00:00",  // createdAt
                        "USER"  // role
                );

                when(customerService.findByUsername(anyString())).thenReturn(Optional.empty());
                when(customerService.registerCustomer(any(CustomerDto.class))).thenReturn(customerResponseDto);

                ResponseEntity<CustomerResponseDto> response = customerController.registerCustomer(customerDto);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertEquals("john_doe", response.getBody().getUsername());
        }

        @Test
        void testAuthenticateAndGetToken() {
                String username = "john_doe";
                String password = "password";

                Customer customer = new Customer();
                customer.setUsername(username);
                customer.setPassword(password);
                customer.setFirstName("John");
                customer.setLastName("Doe");
                customer.setRole(Role.CUSTOMER);

                Authentication authentication = mock(Authentication.class);
                when(authentication.isAuthenticated()).thenReturn(true);
                when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
                when(customerService.findByUsername(username)).thenReturn(Optional.of(customer));
                when(jwtService.generateToken(any())).thenReturn("dummy_token");

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(username);
                loginRequest.setPassword(password);

                ResponseEntity<String> response = customerController.authenticateAndGetToken(loginRequest);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals("dummy_token", response.getBody());
        }
}

