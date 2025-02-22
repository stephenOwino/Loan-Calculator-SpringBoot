package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CustomerMapper {

        // Maps CustomerDto to Customer entity
        public static Customer toEntity(CustomerDto dto) {
                Customer customer = new Customer();
                customer.setFirstName(dto.getFirstName());
                customer.setLastName(dto.getLastName());
                customer.setUsername(dto.getUsername());
                customer.setEmail(dto.getEmail());
                customer.setPassword(dto.getPassword()); // Will be encoded later

                // Set role directly from the DTO
                customer.setRole(dto.getRole());

                return customer;
        }

        // Maps Customer entity to CustomerResponseDto
        public static CustomerResponseDto toDto(Customer customer) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = customer.getCreatedAt().format(formatter);

                // Ensure the ID is always a Long (not primitive long)
                return new CustomerResponseDto(
                        customer.getId() != null ? customer.getId() : null, // Ensure Long type
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUsername(),
                        customer.getEmail(),
                        formattedDate,
                        customer.getRole().name() // Converting the Role enum to a string
                );
        }
}
