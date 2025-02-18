package com.stephenowinoh.Loan_calculator.Repository;

import com.stephenowinoh.Loan_calculator.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

        // Custom query method to find a customer by username
        Optional<Customer> findByUsername(String username);

        // Custom query method to find a customer by email
        Optional<Customer> findByEmail(String email);

}
