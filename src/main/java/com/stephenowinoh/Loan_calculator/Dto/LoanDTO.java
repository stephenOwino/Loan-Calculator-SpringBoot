package com.stephenowinoh.Loan_calculator.Dto;

import com.stephenowinoh.Loan_calculator.Entity.Loan.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanDTO {
        private Long id;
        private Long customerId;
        private BigDecimal amount;
        private BigDecimal totalInterest;
        private BigDecimal totalRepayment;
        private String repaymentFrequency;
        private LocalDateTime createdAt;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int loanTerm;
        private LocalDateTime dueDate;
        private String purpose;
        private LoanStatus status;
        private LocalDateTime paymentDate;
        private BigDecimal interestRate;

        // New fields
        private String fullName;
        private String location;
        private String phoneNumber;
        private String email;

        public LoanDTO() {
        }

        public LoanDTO(Long id, Long customerId, BigDecimal amount, BigDecimal totalInterest, BigDecimal totalRepayment,
                       String repaymentFrequency, LocalDateTime createdAt, LocalDateTime startDate, LocalDateTime endDate,
                       int loanTerm, LocalDateTime dueDate, String purpose, LoanStatus status, LocalDateTime paymentDate,
                       BigDecimal interestRate, String fullName, String location, String phoneNumber, String email) {
                this.id = id;
                this.customerId = customerId;
                this.amount = amount;
                this.totalInterest = totalInterest;
                this.totalRepayment = totalRepayment;
                this.repaymentFrequency = repaymentFrequency;
                this.createdAt = createdAt;
                this.startDate = startDate;
                this.endDate = endDate;
                this.loanTerm = loanTerm;
                this.dueDate = dueDate;
                this.purpose = purpose;
                this.status = status;
                this.paymentDate = paymentDate;
                this.interestRate = interestRate;
                this.fullName = fullName;
                this.location = location;
                this.phoneNumber = phoneNumber;
                this.email = email;
        }

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

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }
}
