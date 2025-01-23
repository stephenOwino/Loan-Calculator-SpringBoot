package com.stephenowinoh.Loan_calculator.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentFrequencyDto {
        private String value;
        private String displayName;  // e.g., DAILY, WEEKLY, MONTHLY, ANNUALLY


}

