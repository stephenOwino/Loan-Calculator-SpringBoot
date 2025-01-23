package com.stephenowinoh.Loan_calculator.Jwt;

public class JwtPayloadDTO {
        private Long id;  // Add ID field
        private String username;
        private String firstName;
        private String lastName;
        private String token;  // Add the token field here

        public JwtPayloadDTO() {
        }

        public JwtPayloadDTO(Long id, String username, String firstName, String lastName, String token) {
                this.id = id;
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.token = token;
        }

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
}
