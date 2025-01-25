package com.stephenowinoh.Loan_calculator.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanDto {

        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
        private BigDecimal amount;
        private BigDecimal totalInterest;
        private BigDecimal totalRepayment;
        private String repaymentFrequency; // Can be a string like "DAILY", "WEEKLY", etc.
        private LocalDateTime createdAt;
        private LocalDateTime dueDate;
        private int loanTerm; // Loan term in years

        public LoanDto() {
        }

        // Constructor for creating LoanDto from a Loan entity
        public LoanDto(Long id, String fullName, String email, String phoneNumber, BigDecimal amount, BigDecimal totalInterest, BigDecimal totalRepayment,
                       String repaymentFrequency, LocalDateTime createdAt, LocalDateTime dueDate, int loanTerm) {
                this.id = id;
                this.fullName = fullName;
                this.email = email;
                this.phoneNumber = phoneNumber;
                this.amount = amount;
                this.totalInterest = totalInterest;
                this.totalRepayment = totalRepayment;
                this.repaymentFrequency = repaymentFrequency;
                this.createdAt = createdAt;
                this.dueDate = dueDate;
                this.loanTerm = loanTerm;
        }
}