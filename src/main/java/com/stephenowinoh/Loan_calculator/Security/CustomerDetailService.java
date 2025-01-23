package com.stephenowinoh.Loan_calculator.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;  // Use CustomerDto
import com.stephenowinoh.Loan_calculator.Entity.Customer;   // The entity for persistence
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository; // Assuming you have a CustomerRepository

import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {

        @Autowired
        private CustomerRepository repository;

        @Override
        public UserDetails loadUserByUsername(String username) {
                // Fetch the customer from the repository
                Optional<Customer> customerOptional = repository.findByUsername(username);

                // Check if customer is present
                if (customerOptional.isPresent()) {
                        // Extract customer entity and convert to DTO
                        CustomerDto customerDto = mapToCustomerDto(customerOptional.get());

                        // Return the UserDetails object (no roles needed for now)
                        return User.builder()
                                .username(customerDto.getUsername())
                                .password(customerDto.getPassword())
                                .build();
                } else {
                        // If no customer found with the given username, throw exception
                        throw new UsernameNotFoundException("User not found with username: " + username);
                }
        }


        // Helper method to map Customer entity to CustomerDto
        private CustomerDto mapToCustomerDto(Customer customer) {
                return new CustomerDto(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUsername(),
                        customer.getEmail(),
                        customer.getPassword(),
                        null  // 'confirmPassword' is not necessary for login
                );
        }
}
