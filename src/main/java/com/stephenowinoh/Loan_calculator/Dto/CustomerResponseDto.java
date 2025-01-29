package com.stephenowinoh.Loan_calculator.Dto;

import com.stephenowinoh.Loan_calculator.Role.Role;

public class CustomerResponseDto {

        private Long id;
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String createdAt;
        private String role; // Added role field as String

        // Default constructor
        public CustomerResponseDto() {
        }

        // Constructor with parameters, converting Role enum to String
        public CustomerResponseDto(Long id, String firstName, String lastName, String username, String email, String createdAt, Role role) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.email = email;
                this.createdAt = createdAt;
                this.role = role.name(); // Convert Role enum to String
        }

        // Getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
        }

        public String getRole() {
                return role; // Getter for role
        }

        public void setRole(String role) {
                this.role = role; // Setter for role
        }
}
