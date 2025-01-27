package com.stephenowinoh.Loan_calculator.Service;


import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceLoan {

        LoanDto createLoan(LoanDto loanDto, String username);

        LoanDto getLoanById(Long id);
        List<LoanDto> getAllLoans();
        LoanDto updateLoan(Long id, LoanDto loanDto);
        void deleteLoan(Long id);
        String generateLoanStatement(Long loanId);

        Page<LoanDto> getAllLoans(int page, int size, String sortBy, String sortDir);
}

