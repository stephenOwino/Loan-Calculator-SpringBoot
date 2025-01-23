package com.stephenowinoh.Loan_calculator.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanDto {

        private Long id;
        private BigDecimal amount;
        private BigDecimal totalInterest;
        private BigDecimal totalRepayment;
        private String repaymentFrequency; // Can be a string like "DAILY", "WEEKLY", etc.
        private LocalDateTime createdAt;
        private LocalDateTime dueDate;

        public LoanDto() {
        }

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

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(BigDecimal amount) {
                this.amount = amount;
        }

        public BigDecimal getTotalInterest() {
                return totalInterest;
        }

        public void setTotalInterest(BigDecimal totalInterest) {
                this.totalInterest = totalInterest;
        }

        public BigDecimal getTotalRepayment() {
                return totalRepayment;
        }

        public void setTotalRepayment(BigDecimal totalRepayment) {
                this.totalRepayment = totalRepayment;
        }

        public String getRepaymentFrequency() {
                return repaymentFrequency;
        }

        public void setRepaymentFrequency(String repaymentFrequency) {
                this.repaymentFrequency = repaymentFrequency;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDateTime dueDate) {
                this.dueDate = dueDate;
        }
}

