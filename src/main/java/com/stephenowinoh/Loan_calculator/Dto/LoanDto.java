package com.stephenowinoh.Loan_calculator.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoanDto {

        private Long id;
        private BigDecimal amount;
        private BigDecimal totalInterest;
        private BigDecimal totalRepayment;
        private String repaymentFrequency; // Can be a string like "DAILY", "WEEKLY", etc.
        private LocalDateTime createdAt;
        private LocalDateTime dueDate; // Updated to match entity

        // Constructor for creating LoanDto from a Loan entity
        public LoanDto(Long id, BigDecimal amount, BigDecimal totalInterest, BigDecimal totalRepayment,
                       String repaymentFrequency, LocalDateTime createdAt, LocalDateTime dueDate) {
                this.id = id;
                this.amount = amount;
                this.totalInterest = totalInterest;
                this.totalRepayment = totalRepayment;
                this.repaymentFrequency = repaymentFrequency;
                this.createdAt = createdAt;
                this.dueDate = dueDate;
        }
}

