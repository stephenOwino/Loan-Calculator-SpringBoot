
package com.stephenowinoh.Loan_calculator.Service;

import com.stephenowinoh.Loan_calculator.Dto.LoanDTO;
import com.stephenowinoh.Loan_calculator.Entity.Customer;
import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Exception.CustomerNotFoundException;
import com.stephenowinoh.Loan_calculator.Exception.LoanNotFoundException;
import com.stephenowinoh.Loan_calculator.Exception.NameMismatchException;
import com.stephenowinoh.Loan_calculator.Mapper.LoanMapper;
import com.stephenowinoh.Loan_calculator.Repository.CustomerRepository;
import com.stephenowinoh.Loan_calculator.Repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        private static final Logger logger = LoggerFactory.getLogger(ServiceLoan.class);

        private final LoanRepository loanRepository;
        private final CustomerRepository customerRepository;

        public ServiceLoan(LoanRepository loanRepository, CustomerRepository customerRepository) {
                this.loanRepository = loanRepository;
                this.customerRepository = customerRepository;
        }

        @Override
        public LoanDTO createLoan(LoanDTO loanDto, String username) {
                logger.info("Attempting to create loan for user: {}", username);

                Customer customer = customerRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: " + username));

                // Verify full name matches registered name
                String registeredFullName = customer.getFirstName() + " " + customer.getLastName();
                if (!registeredFullName.equalsIgnoreCase(loanDto.getFullName())) {
                        logger.warn("Full name does not match registered user details for username: {}", username);
                        throw new NameMismatchException("Full name does not match registered user details. Please ensure the name is correct.");
                }


                // Check the number of active loans
                List<Loan> activeLoans = loanRepository.findByCustomerAndEndDateAfter(customer, LocalDateTime.now());
                if (activeLoans.size() >= 3) {
                        logger.warn("User: {} cannot have more than three active loans", username);
                        throw new IllegalStateException("Customer cannot have more than three active loans.");
                }

                // Calculate totalInterest and totalRepayment
                BigDecimal interestRate = calculateInterestRate(loanDto.getAmount());
                BigDecimal totalInterest = loanDto.getAmount().multiply(interestRate);
                BigDecimal totalRepayment = loanDto.getAmount().add(totalInterest);

                Loan loan = LoanMapper.toEntity(loanDto);
                loan.setCustomer(customer);
                loan.setTotalInterest(totalInterest);
                loan.setTotalRepayment(totalRepayment);
                loan.setStartDate(LocalDateTime.now());
                loan.setEndDate(loan.getStartDate().plusMonths(loan.getLoanTerm())); // Set endDate based on loan term
                loan.setDueDate(loan.getEndDate()); // Assuming dueDate is the same as endDate
                loan.setPurpose(loanDto.getPurpose()); // Set the purpose field
                loan.setInterestRate(interestRate);

                // Set new fields
                loan.setFullName(loanDto.getFullName());
                loan.setLocation(loanDto.getLocation());
                loan.setPhoneNumber(loanDto.getPhoneNumber());
                loan.setEmail(loanDto.getEmail());

                Loan savedLoan = loanRepository.save(loan);
                logger.info("Loan created successfully for user: {}", username);

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
        public LoanDTO getLoanById(Long id) {
                logger.info("Retrieving loan with ID: {}", id);

                Loan loan = loanRepository.findById(id)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));
                return LoanMapper.toDto(loan);
        }

        @Override
        public List<LoanDTO> getAllLoans() {
                logger.info("Retrieving all loans");

                return loanRepository.findAll()
                        .stream()
                        .map(LoanMapper::toDto)
                        .collect(Collectors.toList());
        }

        @Override
        public LoanDTO updateLoan(Long id, LoanDTO loanDto) {
                logger.info("Updating loan with ID: {}", id);

                Loan existingLoan = loanRepository.findById(id)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));

                existingLoan.setAmount(loanDto.getAmount());
                existingLoan.setTotalInterest(loanDto.getTotalInterest());
                existingLoan.setTotalRepayment(loanDto.getTotalRepayment());
                existingLoan.setDueDate(loanDto.getDueDate());
                existingLoan.setLoanTerm(loanDto.getLoanTerm());
                existingLoan.setPurpose(loanDto.getPurpose());

                // Update new fields
                existingLoan.setFullName(loanDto.getFullName());
                existingLoan.setLocation(loanDto.getLocation());
                existingLoan.setPhoneNumber(loanDto.getPhoneNumber());
                existingLoan.setEmail(loanDto.getEmail());

                Loan updatedLoan = loanRepository.save(existingLoan);
                logger.info("Loan with ID: {} updated successfully", id);
                return LoanMapper.toDto(updatedLoan);
        }

        @Override
        public void deleteLoan(Long id) {
                logger.info("Attempting to delete loan with ID: {}", id);

                if (!loanRepository.existsById(id)) {
                        throw new LoanNotFoundException("Loan not found with ID: " + id);
                }
                loanRepository.deleteById(id);
                logger.info("Loan with ID: {} deleted successfully", id);
        }

        public void cancelLoan(Long id) {
                logger.info("Attempting to cancel loan with ID: {}", id);

                Loan loan = loanRepository.findById(id)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));
                loan.setStatus(Loan.LoanStatus.CANCELED);
                loanRepository.save(loan);
                logger.info("Loan with ID: {} canceled successfully", id);
        }

        @Override
        public String generateLoanStatement(Long loanId) {
                logger.info("Generating statement for loan with ID: {}", loanId);

                Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));

                // Simple loan statement generation
                return String.format(
                        "Loan Statement:\nLoan ID: %d\nCustomer ID: %d\nAmount: %.2f\nInterest: %.2f\nTotal Repayment: %.2f\nDue Date: %s",
                        loan.getId(),
                        loan.getCustomer().getId(),
                        loan.getAmount(),
                        loan.getTotalInterest(),
                        loan.getTotalRepayment(),
                        loan.getDueDate()
                );
        }

        public String generateLoanStatementForUser(String username) {
                logger.info("Generating statement for user: {}", username);

                Customer customer = customerRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: " + username));

                List<Loan> loans = loanRepository.findByCustomer(customer);
                if (loans.isEmpty()) {
                        throw new LoanNotFoundException("No loans found for customer");
                }

                Loan loan = loans.get(0); // Assuming we want the statement for the first loan

                // Simple loan statement generation
                return String.format(
                        "Loan Statement:\nLoan ID: %d\nCustomer ID: %d\nAmount: %.2f\nInterest: %.2f\nTotal Repayment: %.2f\nDue Date: %s",
                        loan.getId(),
                        loan.getCustomer().getId(),
                        loan.getAmount(),
                        loan.getTotalInterest(),
                        loan.getTotalRepayment(),
                        loan.getDueDate()
                );
        }

        @Override
        public Page<LoanDTO> getAllLoans(int page, int size, String sortBy, String sortDir) {
                logger.info("Retrieving paginated loans: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(page, size, sort);
                Page<Loan> loans = loanRepository.findAll(pageable);
                return loans.map(LoanMapper::toDto);
        }
}
