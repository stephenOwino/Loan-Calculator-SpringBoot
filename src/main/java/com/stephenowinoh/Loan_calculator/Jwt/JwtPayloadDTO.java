package com.stephenowinoh.Loan_calculator.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayloadDTO {
        private String username;
        private String firstName;
        private String lastName;
        private String token;  // Add the token field here
}
