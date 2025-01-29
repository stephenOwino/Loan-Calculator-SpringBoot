package com.stephenowinoh.Loan_calculator.Dto;

import com.stephenowinoh.Loan_calculator.Entity.Loan.LoanStatus;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanDTO {
        private Long id;
        private Long customerId; // Instead of fullName, email, phoneNumber, just reference customer ID
        private BigDecimal amount;
        private BigDecimal totalInterest;
        private BigDecimal totalRepayment;
        private RepaymentFrequency repaymentFrequency; // This should stay as RepaymentFrequency
        private LocalDateTime createdAt;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int loanTerm; // Loan term in months
        private LocalDateTime dueDate;
        private String purpose;
        private LoanStatus status;
        private LocalDateTime paymentDate;
        private BigDecimal interestRate;

        // Getters and Setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getCustomerId() {
                return customerId;
        }

        public void setCustomerId(Long customerId) {
                this.customerId = customerId;
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
                return repaymentFrequency; // Corrected to return RepaymentFrequency type
        }

        public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
                this.repaymentFrequency = repaymentFrequency; // Corrected setter
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
                this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
                this.endDate = endDate;
        }

        public int getLoanTerm() {
                return loanTerm;
        }

        public void setLoanTerm(int loanTerm) {
                this.loanTerm = loanTerm;
        }

        public LocalDateTime getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDateTime dueDate) {
                this.dueDate = dueDate;
        }

        public String getPurpose() {
                return purpose;
        }

        public void setPurpose(String purpose) {
                this.purpose = purpose;
        }

        public LoanStatus getStatus() {
                return status;
        }

        public void setStatus(LoanStatus status) {
                this.status = status;
        }

        public LocalDateTime getPaymentDate() {
                return paymentDate;
        }

        public void setPaymentDate(LocalDateTime paymentDate) {
                this.paymentDate = paymentDate;
        }

        public BigDecimal getInterestRate() {
                return interestRate;
        }

        public void setInterestRate(BigDecimal interestRate) {
                this.interestRate = interestRate;
        }
}
