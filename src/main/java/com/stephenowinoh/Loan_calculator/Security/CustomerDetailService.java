package com.stephenowinoh.Loan_calculator.Security;

import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {

        @Autowired
        private CustomerRepository repository;

        @Override
        public UserDetails loadUserByUsername(String username) {
                // Fetch the customer from the repository
                Optional<Customer> customerOptional = repository.findByUsername(username);

                // Check if customer exists
                if (customerOptional.isEmpty()) {
                        throw new UsernameNotFoundException("User not found with username: " + username);
                }

                // Return the Customer entity as UserDetails
                Customer customer = customerOptional.get();

                // Validate roles (this will automatically handle authorities in Spring Security)
                if (customer.getAuthorities().isEmpty()) {
                        throw new UsernameNotFoundException("User has no roles assigned: " + username);
                }

                return customer;
        }
}
