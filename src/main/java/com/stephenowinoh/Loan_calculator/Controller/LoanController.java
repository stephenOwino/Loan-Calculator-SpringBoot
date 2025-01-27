package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import com.stephenowinoh.Loan_calculator.Service.ServiceLoan;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

        private final ServiceLoan serviceLoan;

        public LoanController(ServiceLoan serviceLoan) {
                this.serviceLoan = serviceLoan;
        }

        // Create a loan for a customer
        @PostMapping
        public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loanDto, @AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                LoanDto createdLoan = serviceLoan.createLoan(loanDto, username);
                return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        }

        // Get a loan by its ID
        @GetMapping("/{id}")
        public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id) {
                LoanDto loanDto = serviceLoan.getLoanById(id);
                return new ResponseEntity<>(loanDto, HttpStatus.OK);
        }

        // Get all loans
        @GetMapping
        public ResponseEntity<List<LoanDto>> getAllLoans() {
                List<LoanDto> loans = serviceLoan.getAllLoans();
                return new ResponseEntity<>(loans, HttpStatus.OK);
        }

        // Update a loan by its ID
        @PutMapping("/{id}")
        public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id, @RequestBody LoanDto loanDto) {
                LoanDto updatedLoan = serviceLoan.updateLoan(id, loanDto);
                return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        }

        // Delete a loan by its ID
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
                serviceLoan.deleteLoan(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Generate loan statement for the authenticated user
        @GetMapping("/statement")
        public ResponseEntity<String> generateLoanStatement(@AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                String statement = serviceLoan.generateLoanStatementForUser(username);
                return new ResponseEntity<>(statement, HttpStatus.OK);
        }

        // Get loans with pagination and sorting
        @GetMapping("/paginated")
        public ResponseEntity<Page<LoanDto>> getAllLoansWithPagination(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {
                Page<LoanDto> loans = serviceLoan.getAllLoans(page, size, sortBy, sortDir);
                return new ResponseEntity<>(loans, HttpStatus.OK);
        }
}