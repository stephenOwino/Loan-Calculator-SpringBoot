package com.stephenowinoh.Loan_calculator.Mapper;


import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;

import java.time.format.DateTimeFormatter;

public class CustomerMapper {

        // Maps CustomerDto to Customer entity
        public static Customer toEntity(CustomerDto dto) {
                Customer customer = new Customer();
                customer.setFirstName(dto.getFirstName());
                customer.setLastName(dto.getLastName());
                customer.setUsername(dto.getUsername());
                customer.setEmail(dto.getEmail());
                customer.setPassword(dto.getPassword()); // Will be encoded later
                return customer;
        }

        // Maps Customer entity to CustomerResponseDto
        public static CustomerResponseDto toDto(Customer customer) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = customer.getCreatedAt().format(formatter);

                return new CustomerResponseDto(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getUsername(),
                        customer.getEmail(),
                        formattedDate
                );
        }
}

