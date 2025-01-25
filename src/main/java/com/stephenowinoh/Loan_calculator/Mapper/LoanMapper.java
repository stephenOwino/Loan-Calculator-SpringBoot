package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class LoanMapper {

        public static LoanDto toDto(Loan loan) {
                if (loan == null) return null;

                return new LoanDto(
                        loan.getId(),
                        loan.getFullName(),
                        loan.getEmail(),
                        loan.getPhoneNumber(),
                        loan.getAmount(),
                        loan.getTotalInterest(),
                        loan.getTotalRepayment(),
                        loan.getRepaymentFrequency() != null ? loan.getRepaymentFrequency().name() : null,
                        loan.getCreatedAt(),
                        loan.getDueDate(),
                        loan.getLoanTerm()
                );
        }

        public static Loan toEntity(LoanDto loanDto) {
                if (loanDto == null) return null;

                Loan loan = new Loan();
                loan.setFullName(loanDto.getFullName());
                loan.setEmail(loanDto.getEmail());
                loan.setPhoneNumber(loanDto.getPhoneNumber());
                loan.setAmount(loanDto.getAmount());
                loan.setTotalInterest(loanDto.getTotalInterest());
                loan.setTotalRepayment(loanDto.getTotalRepayment());
                loan.setRepaymentFrequency(
                        loanDto.getRepaymentFrequency() != null
                                ? RepaymentFrequency.valueOf(loanDto.getRepaymentFrequency())
                                : null
                );
                loan.setCreatedAt(loanDto.getCreatedAt());
                loan.setDueDate(loanDto.getDueDate());
                loan.setLoanTerm(loanDto.getLoanTerm());
                return loan;
        }
}