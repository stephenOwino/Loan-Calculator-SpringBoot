package com.stephenowinoh.Loan_calculator.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "loans")
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @Column(nullable = false)
        @DecimalMin(value = "0.0", inclusive = false, message = "Loan amount must be greater than zero.")
        private BigDecimal amount;

        @Column(nullable = false)
        private BigDecimal totalInterest;

        @Column(nullable = false)
        private BigDecimal totalRepayment;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RepaymentFrequency repaymentFrequency;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(nullable = false)
        private LocalDateTime startDate;

        @Column(nullable = false)
        private LocalDateTime endDate;

        @Column(nullable = false)
        private int loanTerm; // Loan term in months

        @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        private RepaymentPlan repaymentPlan;

        @Column(nullable = false)
        private LocalDateTime dueDate;

        @Column(nullable = false)
        private String purpose;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LoanStatus status;

        @Column(nullable = true)
        private LocalDateTime paymentDate;

        @Column(nullable = false)
        private BigDecimal interestRate;

        // Getters and Setters

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public @DecimalMin(value = "0.0", inclusive = false, message = "Loan amount must be greater than zero.") BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(@DecimalMin(value = "0.0", inclusive = false, message = "Loan amount must be greater than zero.") BigDecimal amount) {
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

        public RepaymentFrequency getRepaymentFrequency() {
                return repaymentFrequency;
        }

        public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
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

        public RepaymentPlan getRepaymentPlan() {
                return repaymentPlan;
        }

        public void setRepaymentPlan(RepaymentPlan repaymentPlan) {
                this.repaymentPlan = repaymentPlan;
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

        // ...existing code...

        @PrePersist
        protected void onCreate() {
                this.startDate = (this.startDate != null) ? this.startDate : LocalDateTime.now();
                this.endDate = this.startDate.plusMonths(this.loanTerm);
                this.dueDate = this.endDate;
                this.status = LoanStatus.PENDING;
        }

        public enum LoanStatus {
                PENDING,
                APPROVED,
                REJECTED,
                COMPLETED
        }
}