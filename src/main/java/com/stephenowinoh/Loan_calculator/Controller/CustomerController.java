package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Exception.BadRequestException;
import com.stephenowinoh.Loan_calculator.Service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

        private final ICustomerService customerService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        public CustomerController(ICustomerService customerService, AuthenticationManager authenticationManager) {
                this.customerService = customerService;
                this.authenticationManager = authenticationManager;
        }

        @PostMapping("/register")
        public ResponseEntity<CustomerResponseDto> registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
                Optional<Customer> existingCustomer = customerService.findByUsername(customerDto.getUsername());
                if (existingCustomer.isPresent()) {
                        throw new BadRequestException("Username already exists");
                }
                CustomerResponseDto responseDto = customerService.registerCustomer(customerDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }

        @PostMapping("/authenticate")
        public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
                String username = loginRequest.getUsername();
                String password = loginRequest.getPassword();

                if (username == null || password == null) {
                        return ResponseEntity.badRequest().body("Username and password must be provided");
                }

                try {
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password)
                        );

                        if (authentication.isAuthenticated()) {
                                String jwtToken = "your_token"; // Generate your JWT token here
                                return ResponseEntity.ok(jwtToken); // Return the JWT token
                        } else {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                        }
                } catch (BadCredentialsException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                }
        }
}
