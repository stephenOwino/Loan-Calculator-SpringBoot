package com.stephenowinoh.Loan_calculator.Controller;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Service.ServiceLoan;
import com.stephenowinoh.Loan_calculator.Service.ICustomerService;
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
        private final ICustomerService customerService;

        public LoanController(ServiceLoan loanService, ICustomerService customerService) {
                this.loanService = loanService;
                this.customerService = customerService;
        }

        @PostMapping
        public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDto, @AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();

                // Check if the full name matches the registered first and last name
                if (!customerService.verifyCustomerName(loanDto.getFullName(), username)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }

                LoanDTO createdLoan = loanService.createLoan(loanDto, username);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        }

        @GetMapping("/{loanId}")
        public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long loanId) {
                LoanDTO loanDto = loanService.getLoanById(loanId);
                return ResponseEntity.status(HttpStatus.OK).body(loanDto);
        }

        @GetMapping
        public ResponseEntity<List<LoanDTO>> getAllLoans() {
                List<LoanDTO> loans = loanService.getAllLoans();
                return ResponseEntity.status(HttpStatus.OK).body(loans);
        }

        @PutMapping("/{loanId}")
        public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long loanId, @RequestBody LoanDTO loanDto) {
                LoanDTO updatedLoan = loanService.updateLoan(loanId, loanDto);
                return ResponseEntity.status(HttpStatus.OK).body(updatedLoan);
        }

        @DeleteMapping("/{loanId}")
        public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
                loanService.deleteLoan(loanId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @GetMapping("/statement")
        public ResponseEntity<String> generateLoanStatement(@AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                String statement = loanService.generateLoanStatementForUser(username);
                return ResponseEntity.status(HttpStatus.OK).body(statement);
        }

        @GetMapping("/statement/download")
        public ResponseEntity<ByteArrayResource> downloadLoanStatement(@AuthenticationPrincipal UserDetails userDetails) {
                String username = userDetails.getUsername();
                String statement = loanService.generateLoanStatementForUser(username);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                        byteArrayOutputStream.write(statement.getBytes());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }

                ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("Content-Disposition", "attachment;filename=loan_statement.txt")
                        .body(resource);
        }

        @GetMapping("/paginated")
        public ResponseEntity<Page<LoanDTO>> getAllLoansWithPagination(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir) {

                Page<LoanDTO> loans = loanService.getAllLoans(page, size, sortBy, sortDir);
                return ResponseEntity.status(HttpStatus.OK).body(loans);
        }

        @PutMapping("/{loanId}/cancel")
        public ResponseEntity<String> cancelLoan(@PathVariable Long loanId) {
                loanService.cancelLoan(loanId);
                return ResponseEntity.status(HttpStatus.OK).body("Loan canceled successfully");
        }
}



