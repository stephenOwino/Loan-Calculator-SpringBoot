package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Service.ServiceLoan;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

        private final ServiceLoan loanService;

        public LoanController(ServiceLoan loanService) {
                this.loanService = loanService;
        }

        /**
         * Create a new loan for the authenticated customer.
         *
         * @param loanDto the loan details to be created.
         * @param userDetails the authenticated user's details.
         * @return the created loan details.
         */
        @PostMapping
        public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDto,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                LoanDTO createdLoan = loanService.createLoan(loanDto, username);
                return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        }

        /**
         * Get a loan by its ID.
         *
         * @param loanId the ID of the loan to retrieve.
         * @return the loan details.
         */
        @GetMapping("/{loanId}")
        public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long loanId) {
                LoanDTO loanDto = loanService.getLoanById(loanId);
                return new ResponseEntity<>(loanDto, HttpStatus.OK);
        }

        /**
         * Get all loans for the authenticated user.
         *
         * @return a list of all loans.
         */
        @GetMapping
        public ResponseEntity<List<LoanDTO>> getAllLoans() {
                List<LoanDTO> loans = loanService.getAllLoans();
                return new ResponseEntity<>(loans, HttpStatus.OK);
        }

        /**
         * Update an existing loan.
         *
         * @param loanId the ID of the loan to update.
         * @param loanDto the updated loan details.
         * @return the updated loan details.
         */
        @PutMapping("/{loanId}")
        public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long loanId, @RequestBody LoanDTO loanDto) {
                LoanDTO updatedLoan = loanService.updateLoan(loanId, loanDto);
                return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        }

        /**
         * Delete a loan by its ID.
         *
         * @param loanId the ID of the loan to delete.
         * @return a no-content response.
         */
        @DeleteMapping("/{loanId}")
        public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
                loanService.deleteLoan(loanId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        /**
         * Generate a loan statement for the authenticated user.
         *
         * @param userDetails the authenticated user's details.
         * @return the generated loan statement.
         */
        @GetMapping("/statement")
        public ResponseEntity<String> generateLoanStatement(@AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                String statement = loanService.generateLoanStatementForUser(username);
                return new ResponseEntity<>(statement, HttpStatus.OK);
        }

        /**
         * Download the loan statement for the authenticated user as a text file.
         *
         * @param userDetails the authenticated user's details.
         * @return the loan statement file.
         */
        @GetMapping("/statement/download")
        public ResponseEntity<ByteArrayResource> downloadLoanStatement(@AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                String statement = loanService.generateLoanStatementForUser(username);

                // Convert statement to byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                        byteArrayOutputStream.write(statement.getBytes());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }

                // Return file as downloadable resource
                ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("Content-Disposition", "attachment;filename=loan_statement.txt")
                        .body(resource);
        }

        /**
         * Get all loans with pagination and sorting.
         *
         * @param page the page number.
         * @param size the page size.
         * @param sortBy the field to sort by.
         * @param sortDir the direction of sorting (asc or desc).
         * @return paginated loans.
         */
        @GetMapping("/paginated")
        public ResponseEntity<Page<LoanDTO>> getAllLoansWithPagination(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {

                Page<LoanDTO> loans = loanService.getAllLoans(page, size, sortBy, sortDir);
                return new ResponseEntity<>(loans, HttpStatus.OK);
        }
}



