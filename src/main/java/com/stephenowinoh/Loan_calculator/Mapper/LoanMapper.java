package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Entity.Loan;

public class LoanMapper {

        // Method to convert LoanDTO to Loan entity
        public static Loan toEntity(LoanDTO loanDto) {
                if (loanDto == null) {
                        return null;
                }

                Loan loan = new Loan();
                loan.setId(loanDto.getId());
                loan.setAmount(loanDto.getAmount());
                loan.setTotalInterest(loanDto.getTotalInterest());
                loan.setTotalRepayment(loanDto.getTotalRepayment());
                loan.setRepaymentFrequency(loanDto.getRepaymentFrequency());
                loan.setCreatedAt(loanDto.getCreatedAt());
                loan.setStartDate(loanDto.getStartDate());
                loan.setEndDate(loanDto.getEndDate());
                loan.setLoanTerm(loanDto.getLoanTerm());
                loan.setDueDate(loanDto.getDueDate());
                loan.setPurpose(loanDto.getPurpose());
                loan.setStatus(loanDto.getStatus());
                loan.setPaymentDate(loanDto.getPaymentDate());
                loan.setInterestRate(loanDto.getInterestRate());

                return loan;
        }

        // ✅  THIS METHOD TO FIX THE ERROR
        public static LoanDTO toDto(Loan loan) {
                if (loan == null) {
                        return null;
                }

                LoanDTO loanDto = new LoanDTO();
                loanDto.setId(loan.getId());
                loanDto.setAmount(loan.getAmount());
                loanDto.setTotalInterest(loan.getTotalInterest());
                loanDto.setTotalRepayment(loan.getTotalRepayment());
                loanDto.setRepaymentFrequency(loan.getRepaymentFrequency());
                loanDto.setCreatedAt(loan.getCreatedAt());
                loanDto.setStartDate(loan.getStartDate());
                loanDto.setEndDate(loan.getEndDate());
                loanDto.setLoanTerm(loan.getLoanTerm());
                loanDto.setDueDate(loan.getDueDate());
                loanDto.setPurpose(loan.getPurpose());
                loanDto.setStatus(loan.getStatus());
                loanDto.setPaymentDate(loan.getPaymentDate());
                loanDto.setInterestRate(loan.getInterestRate());

                return loanDto;
        }
}
