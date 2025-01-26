package com.stephenowinoh.Loan_calculator.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "loans")
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String fullName;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String phoneNumber;

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

        @Column(nullable = false)
        private int loanTerm; // Loan term in years

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        private RepaymentPlan repaymentPlan;

        @Column(nullable = false)
        private LocalDateTime dueDate;

        @Column(nullable = false)
        private String purpose; // New field for the purpose of the loan

        public Loan() {
        }

        // Getters and setters for all fields

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
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

        public int getLoanTerm() {
                return loanTerm;
        }

        public void setLoanTerm(int loanTerm) {
                this.loanTerm = loanTerm;
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

        public String getPurpose() {
                return purpose;
        }

        public void setPurpose(String purpose) {
                this.purpose = purpose;
        }

        @PrePersist
        protected void onCreate() {
                this.createdAt = LocalDateTime.now();
                this.startDate = this.createdAt;
                this.endDate = this.startDate.plusYears(this.loanTerm); // Set endDate based on loan term
                this.dueDate = this.endDate; // Assuming dueDate is the same as endDate
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