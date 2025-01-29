package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class LoanMapper {

        // Method to convert LoanDTO to Loan entity
        public static Loan toEntity(LoanDTO loanDto) {
                if (loanDto == null) {
                        return null;
                }

                Loan loan = new Loan();
                loan.setId(loanDto.getId());
                // Assuming you fetch the customer from the database by ID
                // loan.setCustomer(fetchCustomerById(loanDto.getCustomerId()));
                loan.setAmount(loanDto.getAmount());
                loan.setTotalInterest(loanDto.getTotalInterest());
                loan.setTotalRepayment(loanDto.getTotalRepayment());

                // Handle enum conversion directly
                if (loanDto.getRepaymentFrequency() != null) {
                        loan.setRepaymentFrequency(loanDto.getRepaymentFrequency());  // Direct assignment if RepaymentFrequency is an enum
                }

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
}
