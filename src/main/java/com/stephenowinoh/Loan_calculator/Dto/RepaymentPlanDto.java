package com.stephenowinoh.Loan_calculator.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class RepaymentPlanDto {

        private Long loanId;  // ID of the associated loan

        private LocalDate startDate;  // Start date of repayment

        private LocalDate endDate;    // End date of repayment

        private BigDecimal installmentAmount;  // Amount per installment

        private List<LocalDate> repaymentDates;  // List of repayment dates

        public RepaymentPlanDto() {
        }

        public RepaymentPlanDto(Long loanId, LocalDate startDate, LocalDate endDate, BigDecimal installmentAmount, List<LocalDate> repaymentDates) {
                this.loanId = loanId;
                this.startDate = startDate;
                this.endDate = endDate;
                this.installmentAmount = installmentAmount;
                this.repaymentDates = repaymentDates;
        }


}
