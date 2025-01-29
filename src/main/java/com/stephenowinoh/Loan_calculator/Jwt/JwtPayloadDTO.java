package com.stephenowinoh.Loan_calculator.Jwt;

import com.stephenowinoh.Loan_calculator.Role.Role;
import java.util.List;
import java.util.stream.Collectors;

public class JwtPayloadDTO {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String token;
        private List<String> roles; // Store roles as strings

        // Default constructor
        public JwtPayloadDTO() {
        }

        // Constructor accepting List<Role> enums
        public JwtPayloadDTO(Long id, String username, String firstName, String lastName, String token, List<Role> roles) {
                this.id = id;
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.token = token;
                // Convert the Role enums to strings and set them
                this.roles = roles.stream().map(Role::name).collect(Collectors.toList());
        }

        // Getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
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

        public String getToken() {
                return token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public List<String> getRoles() {
                return roles;
        }

        public void setRoles(List<String> roles) {
                this.roles = roles;
        }
}
