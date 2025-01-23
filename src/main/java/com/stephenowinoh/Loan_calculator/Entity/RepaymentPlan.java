package com.stephenowinoh.Loan_calculator.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RepaymentPlan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "loan_id", nullable = false)
        private Loan loan;

        @Column(nullable = false)
        private LocalDate startDate; // When repayment starts

        @Column(nullable = false)
        private LocalDate endDate; // When repayment ends

        @Column(nullable = false)
        private BigDecimal installmentAmount; // Amount per repayment

        @ElementCollection
        @CollectionTable(name = "repayment_schedule", joinColumns = @JoinColumn(name = "repayment_plan_id"))
        @Column(name = "repayment_date")
        private List<LocalDate> repaymentDates; // List of repayment dates


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Loan getLoan() {
                return loan;
        }

        public void setLoan(Loan loan) {
                this.loan = loan;
        }

        public LocalDate getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDate startDate) {
                this.startDate = startDate;
        }

        public LocalDate getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDate endDate) {
                this.endDate = endDate;
        }

        public BigDecimal getInstallmentAmount() {
                return installmentAmount;
        }

        public void setInstallmentAmount(BigDecimal installmentAmount) {
                this.installmentAmount = installmentAmount;
        }

        public List<LocalDate> getRepaymentDates() {
                return repaymentDates;
        }

        public void setRepaymentDates(List<LocalDate> repaymentDates) {
                this.repaymentDates = repaymentDates;
        }
}
