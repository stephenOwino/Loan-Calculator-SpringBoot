package com.stephenowinoh.Loan_calculator.Dto;

import com.stephenowinoh.Loan_calculator.Role.Role;

public class CustomerDto {

        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String password;
        private String confirmPassword;
        private Role role;  // Role field as an enum

        // Constructors, getters, and setters...

        public CustomerDto() {
        }

        public CustomerDto(String firstName, String lastName, String username, String email, String password, String confirmPassword, Role role) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.email = email;
                this.password = password;
                this.confirmPassword = confirmPassword;
                this.role = role;
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

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getConfirmPassword() {
                return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
                this.confirmPassword = confirmPassword;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }
}
