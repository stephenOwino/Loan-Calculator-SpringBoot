package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class LoanMapper {

        public static Loan toEntity(LoanDto dto) {
                Loan loan = new Loan();
                loan.setFullName(dto.getFullName());
                loan.setEmail(dto.getEmail());
                loan.setPhoneNumber(dto.getPhoneNumber());
                loan.setAmount(dto.getAmount());
                loan.setTotalInterest(dto.getTotalInterest());
                loan.setTotalRepayment(dto.getTotalRepayment());
                loan.setRepaymentFrequency(RepaymentFrequency.valueOf(dto.getRepaymentFrequency()));
                loan.setStartDate(dto.getStartDate());
                loan.setEndDate(dto.getEndDate());
                loan.setLoanTerm(dto.getLoanTerm());
                loan.setDueDate(dto.getDueDate());
                return loan;
        }

        public static LoanDto toDto(Loan loan) {
                LoanDto dto = new LoanDto();
                dto.setId(loan.getId());
                dto.setCustomerId(loan.getCustomer().getId());
                dto.setFullName(loan.getFullName());
                dto.setEmail(loan.getEmail());
                dto.setPhoneNumber(loan.getPhoneNumber());
                dto.setAmount(loan.getAmount());
                dto.setTotalInterest(loan.getTotalInterest());
                dto.setTotalRepayment(loan.getTotalRepayment());
                dto.setRepaymentFrequency(loan.getRepaymentFrequency().name());
                dto.setCreatedAt(loan.getCreatedAt());
                dto.setStartDate(loan.getStartDate());
                dto.setEndDate(loan.getEndDate());
                dto.setLoanTerm(loan.getLoanTerm());
                dto.setDueDate(loan.getDueDate());
                return dto;
        }
}