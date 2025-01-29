package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class LoanMapper {

        public static LoanDTO toDto(Loan loan) {
                LoanDTO loanDTO = new LoanDTO();
                loanDTO.setId(loan.getId());
                loanDTO.setCustomerId(loan.getCustomer().getId());
                loanDTO.setAmount(loan.getAmount());
                loanDTO.setTotalInterest(loan.getTotalInterest());
                loanDTO.setTotalRepayment(loan.getTotalRepayment());

                // Directly map enum (no need for conversion to String)
                loanDTO.setRepaymentFrequency(loan.getRepaymentFrequency().name());

                loanDTO.setCreatedAt(loan.getCreatedAt());
                loanDTO.setStartDate(loan.getStartDate());
                loanDTO.setEndDate(loan.getEndDate());
                loanDTO.setLoanTerm(loan.getLoanTerm());
                loanDTO.setDueDate(loan.getDueDate());
                loanDTO.setPurpose(loan.getPurpose());
                loanDTO.setStatus(loan.getStatus());
                loanDTO.setPaymentDate(loan.getPaymentDate());
                loanDTO.setInterestRate(loan.getInterestRate());
                return loanDTO;
        }

        public static Loan toEntity(LoanDTO loanDTO) {
                Loan loan = new Loan();
                loan.setId(loanDTO.getId());

                // Fetch customer based on customerId from your database or service
                Customer customer = new Customer();
                customer.setId(loanDTO.getCustomerId()); // In a real-world scenario, fetch customer from DB.
                loan.setCustomer(customer);

                loan.setAmount(loanDTO.getAmount());
                loan.setTotalInterest(loanDTO.getTotalInterest());
                loan.setTotalRepayment(loanDTO.getTotalRepayment());

                // Convert repaymentFrequency from String to enum
                RepaymentFrequency repaymentFrequency = RepaymentFrequency.valueOf(loanDTO.getRepaymentFrequency());
                loan.setRepaymentFrequency(repaymentFrequency);

                loan.setCreatedAt(loanDTO.getCreatedAt());
                loan.setStartDate(loanDTO.getStartDate());
                loan.setEndDate(loanDTO.getEndDate());
                loan.setLoanTerm(loanDTO.getLoanTerm());
                loan.setDueDate(loanDTO.getDueDate());
                loan.setPurpose(loanDTO.getPurpose());
                loan.setStatus(loanDTO.getStatus());
                loan.setPaymentDate(loanDTO.getPaymentDate());
                loan.setInterestRate(loanDTO.getInterestRate());

                return loan;
        }
}
