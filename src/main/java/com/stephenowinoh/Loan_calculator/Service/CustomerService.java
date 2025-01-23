package com.stephenowinoh.Loan_calculator.Service;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Mapper.CustomerMapper;
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

        private final CustomerRepository customerRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        @Autowired
        public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
                this.customerRepository = customerRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public Optional<Customer> findByUsername(String username) {
                // Find customer by username
                return customerRepository.findByUsername(username);
        }

        @Override
        public CustomerResponseDto registerCustomer(CustomerDto customerDto) {
                // Check if the username already exists
                Optional<Customer> existingCustomer = customerRepository.findByUsername(customerDto.getUsername());
                if (existingCustomer.isPresent()) {
                        throw new RuntimeException("Username is already taken!");
                }

                // Create and save the customer
                Customer customer = CustomerMapper.toEntity(customerDto);
                customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));  // Encrypt password
                Customer savedCustomer = customerRepository.save(customer);

                // Return the saved customer as a response DTO
                return CustomerMapper.toDto(savedCustomer);
        }

        @Override
        public Optional<CustomerResponseDto> getCustomerDetails(Long id) {
                // Find customer by ID and return as DTO
                Optional<Customer> customer = customerRepository.findById(id);
                return customer.map(CustomerMapper::toDto);
        }

        @Override
        public Optional<CustomerResponseDto> updateCustomer(Long id, CustomerDto customerDto) {
                // Find the existing customer by ID
                Optional<Customer> existingCustomer = customerRepository.findById(id);
                if (existingCustomer.isPresent()) {
                        Customer customer = existingCustomer.get();
                        customer.setFirstName(customerDto.getFirstName());
                        customer.setLastName(customerDto.getLastName());
                        customer.setUsername(customerDto.getUsername());
                        customer.setEmail(customerDto.getEmail());

                        // Encrypt the password if it is provided in the DTO
                        if (customerDto.getPassword() != null && !customerDto.getPassword().isEmpty()) {
                                customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                        }

                        // Save the updated customer
                        Customer updatedCustomer = customerRepository.save(customer);

                        // Return the updated customer as DTO
                        return Optional.of(CustomerMapper.toDto(updatedCustomer));
                }
                return Optional.empty();
        }

        @Override
        public void deleteCustomer(Long id) {
                // Delete customer by ID
                customerRepository.deleteById(id);
        }

        @Override
        public List<CustomerResponseDto> getAllCustomers() {
                // Get all customers and map to response DTOs
                List<Customer> customers = customerRepository.findAll();
                return customers.stream()
                        .map(CustomerMapper::toDto)
                        .collect(Collectors.toList());
        }
}
