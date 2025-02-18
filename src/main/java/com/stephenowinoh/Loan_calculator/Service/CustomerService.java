package com.stephenowinoh.Loan_calculator.Service;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Mapper.CustomerMapper;
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository;
import com.stephenowinoh.Loan_calculator.Role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

        private final CustomerRepository customerRepository;
        private final PasswordEncoder passwordEncoder;

        @Autowired
        public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
                this.customerRepository = customerRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public Optional<Customer> findByUsername(String username) {
                return customerRepository.findByUsername(username);
        }

        @Override
        public Optional<Customer> findByEmail(String email) {
                return customerRepository.findByEmail(email);  // Add this method to check by email
        }

        @Override
        public CustomerResponseDto registerCustomer(CustomerDto customerDto) {
                // Check if username already exists
                Optional<Customer> existingCustomerByUsername = customerRepository.findByUsername(customerDto.getUsername());
                if (existingCustomerByUsername.isPresent()) {
                        throw new RuntimeException("Username is already taken!");
                }

                // Check if email already exists
                Optional<Customer> existingCustomerByEmail = customerRepository.findByEmail(customerDto.getEmail());
                if (existingCustomerByEmail.isPresent()) {
                        throw new RuntimeException("Email is already in use!");
                }

                // Set default role to CUSTOMER if not provided
                Role role = customerDto.getRole() != null ? customerDto.getRole() : Role.CUSTOMER;

                Customer customer = CustomerMapper.toEntity(customerDto);
                customer.setPassword(passwordEncoder.encode(customerDto.getPassword())); // Encrypt password
                customer.setRole(role); // Set role
                customer.setEmail(customerDto.getEmail()); // Set email
                Customer savedCustomer = customerRepository.save(customer);

                return CustomerMapper.toDto(savedCustomer);
        }

        @Override
        public Optional<CustomerResponseDto> getCustomerDetails(Long id) {
                Optional<Customer> customer = customerRepository.findById(id);
                return customer.map(CustomerMapper::toDto);
        }

        @Override
        public Optional<CustomerResponseDto> updateCustomer(Long id, CustomerDto customerDto) {
                Optional<Customer> existingCustomer = customerRepository.findById(id);
                if (existingCustomer.isPresent()) {
                        Customer customer = existingCustomer.get();
                        customer.setFirstName(customerDto.getFirstName());
                        customer.setLastName(customerDto.getLastName());
                        customer.setUsername(customerDto.getUsername());
                        customer.setEmail(customerDto.getEmail());

                        if (customerDto.getPassword() != null && !customerDto.getPassword().isEmpty()) {
                                customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));  // Encrypt password
                        }

                        // Update role if provided
                        if (customerDto.getRole() != null) {
                                customer.setRole(customerDto.getRole());
                        }

                        Customer updatedCustomer = customerRepository.save(customer);
                        return Optional.of(CustomerMapper.toDto(updatedCustomer));
                }
                return Optional.empty();
        }

        @Override
        public void deleteCustomer(Long id) {
                customerRepository.deleteById(id);
        }

        @Override
        public List<CustomerResponseDto> getAllCustomers() {
                List<Customer> customers = customerRepository.findAll();
                return customers.stream()
                        .map(CustomerMapper::toDto)
                        .collect(Collectors.toList());
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Customer> customerOptional = customerRepository.findByUsername(username);
                if (customerOptional.isPresent()) {
                        Customer customer = customerOptional.get();
                        return org.springframework.security.core.userdetails.User.builder()
                                .username(customer.getUsername())
                                .password(customer.getPassword())
                                .authorities("ROLE_" + customer.getRole().name())
                                .build();
                } else {
                        throw new UsernameNotFoundException("User not found with username: " + username);
                }
        }

        // Verify that the full name matches the registered first and last name
        public boolean verifyCustomerName(String fullName, String username) {
                Optional<Customer> customerOpt = customerRepository.findByUsername(username);
                if (customerOpt.isPresent()) {
                        Customer customer = customerOpt.get();
                        String registeredFullName = customer.getFirstName() + " " + customer.getLastName();
                        return registeredFullName.equalsIgnoreCase(fullName);
                }
                return false;
        }
}
