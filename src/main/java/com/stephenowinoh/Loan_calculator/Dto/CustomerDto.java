package com.stephenowinoh.Loan_calculator.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto{
                private String firstName;
                private String lastName;
                private String username;
                private String email;
                private String password;
                private String confirmPassword;
}
