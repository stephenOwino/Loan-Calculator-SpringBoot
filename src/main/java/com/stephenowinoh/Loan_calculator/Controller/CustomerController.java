package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Exception.CustomerNotFoundException;
import com.stephenowinoh.Loan_calculator.Exception.BadRequestException;
import com.stephenowinoh.Loan_calculator.LoginForm;
import com.stephenowinoh.Loan_calculator.Mapper.CustomerMapper;
import com.stephenowinoh.Loan_calculator.Security.CustomerDetailService;
import com.stephenowinoh.Loan_calculator.Service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

        private final ICustomerService customerService;

        @Autowired
        public CustomerController(ICustomerService customerService) {
                this.customerService = customerService;
        }

        // Endpoint to register a new customer
        @PostMapping("/register")
        public ResponseEntity<CustomerResponseDto> registerCustomer(@RequestBody CustomerDto customerDto) {
                try {
                        // Check if customer already exists by username or email, and throw BadRequestException if true
                        Optional<Customer> existingCustomer = customerService.findByUsername(customerDto.getUsername());
                        if (existingCustomer.isPresent()) {
                                throw new BadRequestException("Username already exists");
                        }

                        // Register the new customer and return a CREATED response
                        CustomerResponseDto responseDto = customerService.registerCustomer(customerDto);
                        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
                } catch (BadRequestException e) {
                        // Handle bad request exceptions
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
        }

        // Endpoint to get customer details by ID
        @GetMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> getCustomerDetails(@PathVariable Long id) {
                Optional<CustomerResponseDto> responseDto = customerService.getCustomerDetails(id);
                if (responseDto.isEmpty()) {
                        throw new CustomerNotFoundException("Customer with ID " + id + " not found");
                }
                return new ResponseEntity<>(responseDto.get(), HttpStatus.OK);
        }

        // Endpoint to update customer details
        @PutMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
                Optional<CustomerResponseDto> responseDto = customerService.updateCustomer(id, customerDto);
                if (responseDto.isEmpty()) {
                        throw new CustomerNotFoundException("Customer with ID " + id + " not found for update");
                }
                return new ResponseEntity<>(responseDto.get(), HttpStatus.OK);
        }

        // Endpoint to delete a customer
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
                Optional<Customer> customer = customerService.findByUsername(id.toString());
                if (customer.isEmpty()) {
                        throw new CustomerNotFoundException("Customer with ID " + id + " not found for deletion");
                }
                customerService.deleteCustomer(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Endpoint to get all customers
        @GetMapping("/")
        public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
                List<CustomerResponseDto> customers = customerService.getAllCustomers();
                return new ResponseEntity<>(customers, HttpStatus.OK);
        }

        // Endpoint to find customer by username
        @GetMapping("/username/{username}")
        public ResponseEntity<CustomerResponseDto> getCustomerByUsername(@PathVariable String username) {
                Optional<Customer> customer = customerService.findByUsername(username);
                if (customer.isEmpty()) {
                        throw new CustomerNotFoundException("Customer with username " + username + " not found");
                }
                return new ResponseEntity<>(CustomerMapper.toDto(customer.get()), HttpStatus.OK);
        }

        // Authenticate and generate JWT token
        @PostMapping("/authenticate")
        public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
                try {
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        loginForm.getUsername(),
                                        loginForm.getPassword()
                                )
                        );

                        if (authentication.isAuthenticated()) {
                                String token = jwtService.generateToken(CustomerDetailService.loadUserByUsername(loginForm.getUsername()));
                                return ResponseEntity.ok(token);
                        } else {
                                throw new BadRequestException("Invalid credentials");
                        }
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
                }
        }
}
