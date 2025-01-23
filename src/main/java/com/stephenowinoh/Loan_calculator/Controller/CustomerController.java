package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Exception.BadRequestException;
import com.stephenowinoh.Loan_calculator.Exception.CustomerNotFoundException;
import com.stephenowinoh.Loan_calculator.Jwt.JWTService;
import com.stephenowinoh.Loan_calculator.Jwt.JwtPayloadDTO;
import com.stephenowinoh.Loan_calculator.Mapper.CustomerMapper;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

        private final ICustomerService customerService;
        private final AuthenticationManager authenticationManager;
        private final JWTService jwtService;

        @Autowired
        public CustomerController(ICustomerService customerService, AuthenticationManager authenticationManager, JWTService jwtService) {
                this.customerService = customerService;
                this.authenticationManager = authenticationManager;
                this.jwtService = jwtService;
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


        @GetMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> getCustomerDetails(@PathVariable Long id) {
                return customerService.getCustomerDetails(id)
                        .map(ResponseEntity::ok)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
        }

        @PutMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) {
                return customerService.updateCustomer(id, customerDto)
                        .map(ResponseEntity::ok)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found for update"));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
                customerService.getCustomerDetails(id)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found for deletion"));
                customerService.deleteCustomer(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/")
        public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
                return ResponseEntity.ok(customerService.getAllCustomers());
        }

        @GetMapping("/username/{username}")
        public ResponseEntity<CustomerResponseDto> getCustomerByUsername(@PathVariable String username) {
                return customerService.findByUsername(username)
                        .map(customer -> ResponseEntity.ok(CustomerMapper.toDto(customer)))
                        .orElseThrow(() -> new CustomerNotFoundException("Customer with username " + username + " not found"));
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
                                JwtPayloadDTO customer = customerService.loadUserByUsername(username);
                                String jwtToken = jwtService.generateToken(customer);
                                return ResponseEntity.ok(jwtToken); // Return the JWT token
                        } else {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                        }
                } catch (BadCredentialsException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + e.getMessage());
                }
        }
}
