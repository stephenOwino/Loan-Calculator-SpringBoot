package com.stephenowinoh.Loan_calculator.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        @DecimalMin(value = "0.0", inclusive = false)
        private BigDecimal amount;


        @Column(nullable = false)
        private BigDecimal totalInterest;

        @Column(nullable = false)
        private BigDecimal totalRepayment;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RepaymentFrequency repaymentFrequency;

        @Column(nullable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(nullable = false)
        private LocalDateTime startDate; // Start date of repayment

        @Column(nullable = false)
        private LocalDateTime endDate; // End date of repayment

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        private RepaymentPlan repaymentPlan;

        // If you need a specific dueDate field:
        @Column(nullable = false)
        private LocalDateTime dueDate; // Explicit due date if separate from endDate

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

        // Remaining time in seconds
        public long getRemainingTimeInSeconds() {
                return ChronoUnit.SECONDS.between(LocalDateTime.now(), this.dueDate); // or this.endDate
        }

// Method to return formatted remaining time in days, hours, minutes, seconds
        public String getFormattedRemainingTime() {
                long remainingTimeInSeconds = getRemainingTimeInSeconds();
                long days = remainingTimeInSeconds / (24 * 3600);
                long hours = (remainingTimeInSeconds % (24 * 3600)) / 3600;
                long minutes = (remainingTimeInSeconds % 3600) / 60;
                long seconds = remainingTimeInSeconds % 60;

                return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
        }
}

