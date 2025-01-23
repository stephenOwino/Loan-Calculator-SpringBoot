package com.stephenowinoh.Loan_calculator.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentPlanDto {

        private Long loanId;  // ID of the associated loan

        private LocalDate startDate;  // Start date of repayment

        private LocalDate endDate;    // End date of repayment

        private BigDecimal installmentAmount;  // Amount per installment

        private List<LocalDate> repaymentDates;  // List of repayment dates
}
