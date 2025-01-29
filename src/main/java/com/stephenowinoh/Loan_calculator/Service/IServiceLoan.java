package com.stephenowinoh.Loan_calculator.Service;


import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceLoan {


        LoanDTO createLoan(LoanDTO loanDto, String username);

        LoanDTO getLoanById(Long id);
        List<LoanDTO> getAllLoans();
        LoanDTO updateLoan(Long id, LoanDTO loanDto);
        void deleteLoan(Long id);
        String generateLoanStatement(Long loanId);

        Page<LoanDTO> getAllLoans(int page, int size, String sortBy, String sortDir);
}

