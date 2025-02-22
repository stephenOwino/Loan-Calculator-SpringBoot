package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Exception.BadRequestException;
import com.stephenowinoh.Loan_calculator.Jwt.JWTService;
import com.stephenowinoh.Loan_calculator.Jwt.JwtPayloadDTO;
import com.stephenowinoh.Loan_calculator.Role.Role;
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
                // Check if email already exists
                Optional<Customer> existingCustomerByEmail = customerService.findByEmail(customerDto.getEmail());
                if (existingCustomerByEmail.isPresent()) {
                        throw new BadRequestException("Email is already in use");
                }

                // Check if username already exists
                Optional<Customer> existingCustomerByUsername = customerService.findByUsername(customerDto.getUsername());
                if (existingCustomerByUsername.isPresent()) {
                        throw new BadRequestException("Username is already taken");
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
                        // Authenticate the user
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password)
                        );

                        if (authentication.isAuthenticated()) {
                                // Fetch user from the database
                                Optional<Customer> customer = customerService.findByUsername(username);
                                if (customer.isPresent()) {
                                        // Populate JwtPayloadDTO with user details
                                        Customer user = customer.get();

                                        // Get the role and wrap it in a list
                                        List<Role> roles = List.of(user.getRole());  // Wrap the single role in a list

                                        // Generate JWT token
                                        JwtPayloadDTO payloadDTO = new JwtPayloadDTO(
                                                user.getId(),  // Use user.getId() for customerId
                                                user.getUsername(),
                                                user.getFirstName(),
                                                user.getLastName(),
                                                null, // Token will be populated after generation
                                                roles // Pass roles as a list of Role enums
                                        );

                                        // Generate JWT token
                                        String jwtToken = jwtService.generateToken(payloadDTO);

                                        // Set the token in the payloadDTO
                                        payloadDTO.setToken(jwtToken);

                                        // Return the token in response
                                        return ResponseEntity.ok(jwtToken); // Return the JWT token with customerId
                                } else {
                                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Customer not found");
                                }
                        } else {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                        }
                } catch (BadCredentialsException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                }
        }

        // Get customer by ID
        @GetMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
                Optional<CustomerResponseDto> customerResponseDto = customerService.getCustomerDetails(id);
                return customerResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        // Update customer details
        @PutMapping("/{id}")
        public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
                Optional<CustomerResponseDto> updatedCustomer = customerService.updateCustomer(id, customerDto);
                return updatedCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        // Delete customer
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
                try {
                        customerService.deleteCustomer(id);
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
        }

        // Get all customers
        @GetMapping
        public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
                List<CustomerResponseDto> customers = customerService.getAllCustomers();
                return ResponseEntity.ok(customers);
        }
}
