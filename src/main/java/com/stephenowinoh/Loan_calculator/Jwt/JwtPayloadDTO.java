package com.stephenowinoh.Loan_calculator.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class JwtPayloadDTO {
        private String username;
        private String firstName;
        private String lastName;
        private String token;  // Add the token field here

        public JwtPayloadDTO() {
        }

        public JwtPayloadDTO(String username, String firstName, String lastName, String token) {
                this.username = username;
                this.firstName = firstName;
                this.lastName = lastName;
                this.token = token;
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
