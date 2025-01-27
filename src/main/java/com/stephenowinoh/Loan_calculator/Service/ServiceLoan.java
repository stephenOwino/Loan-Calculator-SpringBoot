package com.stephenowinoh.Loan_calculator.Service;

import com.stephenowinoh.Loan_calculator.Dto.LoanDto;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;
import com.stephenowinoh.Loan_calculator.Exception.CustomerNotFoundException;
import com.stephenowinoh.Loan_calculator.Exception.LoanNotFoundException;
import com.stephenowinoh.Loan_calculator.Mapper.LoanMapper;
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository;
import com.stephenowinoh.Loan_calculator.Repository.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLoan implements IServiceLoan {

        private final LoanRepository loanRepository;
        private final CustomerRepository customerRepository;

        public ServiceLoan(LoanRepository loanRepository, CustomerRepository customerRepository) {
                this.loanRepository = loanRepository;
                this.customerRepository = customerRepository;
        }

        @Override
        public LoanDto createLoan(LoanDto loanDto, String username) {
                Customer customer = customerRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: " + username));

                // Check the number of active loans
                List<Loan> activeLoans = loanRepository.findByCustomerAndEndDateAfter(customer, LocalDateTime.now());
                if (activeLoans.size() >= 3) {
                        throw new IllegalStateException("Customer cannot have more than three active loans.");
                }

                // Calculate totalInterest and totalRepayment
                BigDecimal interestRate = calculateInterestRate(loanDto.getAmount());
                BigDecimal totalInterest = loanDto.getAmount().multiply(interestRate);
                BigDecimal totalRepayment = loanDto.getAmount().add(totalInterest);

                // Implement business logic for partial repayments
                if (activeLoans.size() == 1) {
                        // If the customer has one active loan, allow a second loan with the same amount
                        totalRepayment = loanDto.getAmount().add(totalInterest);
                } else if (activeLoans.size() == 2) {
                        // If the customer has two active loans, allow a third loan with half the amount
                        totalRepayment = loanDto.getAmount().divide(BigDecimal.valueOf(2)).add(totalInterest);
                }

                Loan loan = LoanMapper.toEntity(loanDto);
                loan.setCustomer(customer);
                loan.setTotalInterest(totalInterest);
                loan.setTotalRepayment(totalRepayment);
                loan.setStartDate(LocalDateTime.now());
                loan.setEndDate(loan.getStartDate().plusYears(loan.getLoanTerm())); // Set endDate based on loan term
                loan.setDueDate(loan.getEndDate()); // Assuming dueDate is the same as endDate
                loan.setPurpose(loanDto.getPurpose()); // Set the purpose field
                Loan savedLoan = loanRepository.save(loan);

                return LoanMapper.toDto(savedLoan);
        }

        private BigDecimal calculateInterestRate(BigDecimal amount) {
                if (amount.compareTo(BigDecimal.valueOf(10000)) >= 0 && amount.compareTo(BigDecimal.valueOf(100000)) <= 0) {
                        return BigDecimal.valueOf(0.07);
                } else if (amount.compareTo(BigDecimal.valueOf(100000)) > 0 && amount.compareTo(BigDecimal.valueOf(500000)) <= 0) {
                        return BigDecimal.valueOf(0.05);
                } else if (amount.compareTo(BigDecimal.valueOf(500000)) > 0 && amount.compareTo(BigDecimal.valueOf(1000000)) <= 0) {
                        return BigDecimal.valueOf(0.03);
                } else {
                        throw new IllegalArgumentException("Invalid loan amount");
                }
        }

        @Override
        public LoanDto getLoanById(Long id) {
                Loan loan = loanRepository.findById(id)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));
                return LoanMapper.toDto(loan);
        }

        @Override
        public List<LoanDto> getAllLoans() {
                return loanRepository.findAll()
                        .stream()
                        .map(LoanMapper::toDto)
                        .collect(Collectors.toList());
        }

        @Override
        public LoanDto updateLoan(Long id, LoanDto loanDto) {
                Loan existingLoan = loanRepository.findById(id)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));

                existingLoan.setFullName(loanDto.getFullName());
                existingLoan.setEmail(loanDto.getEmail());
                existingLoan.setPhoneNumber(loanDto.getPhoneNumber());
                existingLoan.setAmount(loanDto.getAmount());
                existingLoan.setTotalInterest(loanDto.getTotalInterest());
                existingLoan.setTotalRepayment(loanDto.getTotalRepayment());
                existingLoan.setRepaymentFrequency(
                        RepaymentFrequency.valueOf(loanDto.getRepaymentFrequency())
                );
                existingLoan.setDueDate(loanDto.getDueDate());
                existingLoan.setLoanTerm(loanDto.getLoanTerm());

                Loan updatedLoan = loanRepository.save(existingLoan);
                return LoanMapper.toDto(updatedLoan);
        }

        @Override
        public void deleteLoan(Long id) {
                if (!loanRepository.existsById(id)) {
                        throw new LoanNotFoundException("Loan not found with ID: " + id);
                }
                loanRepository.deleteById(id);
        }

        @Override
        public String generateLoanStatement(Long loanId) {
                Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));

                // Simple loan statement generation
                return String.format(
                        "Loan Statement:\nLoan ID: %d\nFull Name: %s\nEmail: %s\nPhone Number: %s\nAmount: %.2f\nInterest: %.2f\nTotal Repayment: %.2f\nDue Date: %s",
                        loan.getId(),
                        loan.getFullName(),
                        loan.getEmail(),
                        loan.getPhoneNumber(),
                        loan.getAmount(),
                        loan.getTotalInterest(),
                        loan.getTotalRepayment(),
                        loan.getDueDate()
                );
        }

        @Override
        public Page<LoanDto> getAllLoans(int page, int size, String sortBy, String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                Page<Loan> loans = loanRepository.findAll(pageable);
                return loans.map(LoanMapper::toDto);
        }
}