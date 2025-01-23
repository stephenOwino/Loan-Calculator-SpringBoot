package com.stephenowinoh.Loan_calculator.Service;

import com.stephenowinoh.Loan_calculator.Dto.CustomerDto;
import com.stephenowinoh.Loan_calculator.Dto.CustomerResponseDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Jwt.JwtPayloadDTO;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

        // Method to find a customer by username
        Optional<Customer> findByUsername(String username);

        // Method to register a new customer and return response DTO
        CustomerResponseDto registerCustomer(CustomerDto customerDto);

        // Method to fetch customer details by ID
        Optional<CustomerResponseDto> getCustomerDetails(Long id);

        // Method to update customer details
        Optional<CustomerResponseDto> updateCustomer(Long id, CustomerDto customerDto);

        // Method to delete a customer by ID
        void deleteCustomer(Long id);

        // Method to list all customers
        List<CustomerResponseDto> getAllCustomers();

        JwtPayloadDTO loadUserByUsername(String username);
}
