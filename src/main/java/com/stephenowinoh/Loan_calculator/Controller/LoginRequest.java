package com.stephenowinoh.Loan_calculator.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginRequest {
        private String username;
        private String password;
        // getters and setters


        public LoginRequest() {
        }

        public LoginRequest(String username, String password) {
                this.username = username;
                this.password = password;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}


