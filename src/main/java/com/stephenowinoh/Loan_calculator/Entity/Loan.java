package com.stephenowinoh.Loan_calculator.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stephenowinoh.Loan_calculator.TimeSerializationDecerialization.CustomLocalDateTimeDeserializer;
import com.stephenowinoh.Loan_calculator.TimeSerializationDecerialization.CustomLocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
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
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime createdAt;

        @Column(nullable = false)
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime startDate;

        @Column(nullable = false)
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime endDate;

        @Column(nullable = false)
        private int loanTerm; // Loan term in months

        @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        private RepaymentPlan repaymentPlan;

        @Column(nullable = false)
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime dueDate;

        @Column(nullable = false)
        private String purpose;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LoanStatus status;

        @Column(nullable = true)
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        private LocalDateTime paymentDate;

        @Column(nullable = false)
        private BigDecimal interestRate;

        @Column(nullable = false)
        private String fullName;

        @Column(nullable = false)
        private String location;

        @Column(nullable = false)
        private String phoneNumber;

        @Column(nullable = false)
        private String email;

        public Loan() {}

        public Loan(Long id, Customer customer, BigDecimal amount, BigDecimal totalInterest, BigDecimal totalRepayment,
                    RepaymentFrequency repaymentFrequency, LocalDateTime createdAt, LocalDateTime startDate, LocalDateTime endDate,
                    int loanTerm, RepaymentPlan repaymentPlan, LocalDateTime dueDate, String purpose, LoanStatus status,
                    LocalDateTime paymentDate, BigDecimal interestRate, String fullName, String location, String phoneNumber, String email) {
                this.id = id;
                this.customer = customer;
                this.amount = amount;
                this.totalInterest = totalInterest;
                this.totalRepayment = totalRepayment;
                this.repaymentFrequency = repaymentFrequency;
                this.createdAt = createdAt;
                this.startDate = startDate;
                this.endDate = endDate;
                this.loanTerm = loanTerm;
                this.repaymentPlan = repaymentPlan;
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

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
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
                COMPLETED,
                CANCELED // Add this status
        }
}
