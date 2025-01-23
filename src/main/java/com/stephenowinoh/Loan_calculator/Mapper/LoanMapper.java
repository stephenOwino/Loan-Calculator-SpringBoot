package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class LoanMapper {

        // Map Loan entity to LoanDto
        public static LoanDto toDto(Loan loan) {
                return new LoanDto(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTotalInterest(),
                        loan.getTotalRepayment(),
                        loan.getRepaymentFrequency().name(), // RepaymentFrequency enum as string
                        loan.getCreatedAt(),
                        loan.getDueDate() // Due date mapped from entity
                );
        }

        // Map LoanDto to Loan entity
        public static Loan toEntity(LoanDto loanDto) {
                Loan loan = new Loan();
                loan.setAmount(loanDto.getAmount());
                loan.setTotalInterest(loanDto.getTotalInterest());
                loan.setTotalRepayment(loanDto.getTotalRepayment());
                loan.setRepaymentFrequency(RepaymentFrequency.valueOf(loanDto.getRepaymentFrequency())); // Convert string back to enum
                loan.setCreatedAt(loanDto.getCreatedAt());
                loan.setDueDate(loanDto.getDueDate());
                return loan;
        }
}
