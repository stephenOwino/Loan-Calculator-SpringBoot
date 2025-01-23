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
}
