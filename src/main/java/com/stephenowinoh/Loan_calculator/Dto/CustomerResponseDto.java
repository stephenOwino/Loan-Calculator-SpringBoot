package com.stephenowinoh.Loan_calculator.Dto;

public class CustomerResponseDto {

        private Long id;
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String createdAt;
        private String name; // role as a string (instead of enum)

        // Constructor with role as name field
        public CustomerResponseDto(Long id, String firstName, String lastName, String username, String email, String createdAt, String name) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.email = email;
                this.createdAt = createdAt;
                this.name = name; // role as name
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

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
