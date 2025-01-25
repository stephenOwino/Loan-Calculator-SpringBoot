package com.stephenowinoh.Loan_calculator.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

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

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(nullable = false)
        private LocalDateTime startDate;

        @Column(nullable = false)
        private LocalDateTime endDate;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        private RepaymentPlan repaymentPlan;

        @Column(nullable = false)
        private LocalDateTime dueDate;

        public Loan() {
        }

        public Loan(Long id, BigDecimal amount, BigDecimal totalInterest, BigDecimal totalRepayment, RepaymentFrequency repaymentFrequency, LocalDateTime createdAt, LocalDateTime startDate, LocalDateTime endDate, Customer customer, RepaymentPlan repaymentPlan, LocalDateTime dueDate) {
                this.id = id;
                this.amount = amount;
                this.totalInterest = totalInterest;
                this.totalRepayment = totalRepayment;
                this.repaymentFrequency = repaymentFrequency;
                this.createdAt = createdAt;
                this.startDate = startDate;
                this.endDate = endDate;
                this.customer = customer;
                this.repaymentPlan = repaymentPlan;
                this.dueDate = dueDate;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
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

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
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

        @PrePersist
        protected void onCreate() {
                this.createdAt = LocalDateTime.now();
        }

        public long getRemainingTimeInSeconds() {
                return ChronoUnit.SECONDS.between(LocalDateTime.now(), this.dueDate);
        }

        public String getFormattedRemainingTime() {
                long remainingTime = getRemainingTimeInSeconds();
                long days = remainingTime / (24 * 3600);
                long hours = (remainingTime % (24 * 3600)) / 3600;
                long minutes = (remainingTime % 3600) / 60;
                long seconds = remainingTime % 60;

                return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
        }
}
