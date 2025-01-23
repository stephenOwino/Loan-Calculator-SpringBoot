package com.stephenowinoh.Loan_calculator.Mapper;



import com.stephenowinoh.Loan_calculator.Dto.RepaymentPlanDto;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentPlan;

public class RepaymentPlanMapper {

        public static RepaymentPlanDto toDto(RepaymentPlan repaymentPlan) {
                return new RepaymentPlanDto(
                        repaymentPlan.getLoan().getId(),  // Loan ID
                        repaymentPlan.getStartDate(),     // Start Date
                        repaymentPlan.getEndDate(),       // End Date
                        repaymentPlan.getInstallmentAmount(),  // Installment Amount
                        repaymentPlan.getRepaymentDates()  // Repayment Dates
                );
        }
}

