package com.stephenowinoh.Loan_calculator.Repository;


import com.stephenowinoh.Loan_calculator.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

        // Find all loans by a specific customer
        List<Loan> findByCustomerId(Long customerId);

        // Find all loans with a due date before a certain date
        List<Loan> findByDueDateBefore(LocalDateTime date);

        // Find all loans with a due date after a certain date
        List<Loan> findByDueDateAfter(LocalDateTime date);

        // Find all loans by repayment frequency
        List<Loan> findByRepaymentFrequency(String repaymentFrequency);

        // Optional: Add custom queries if needed (e.g., for overdue loans)
        // @Query("SELECT l FROM Loan l WHERE l.dueDate < :currentDate")
        // List<Loan> findOverdueLoans(@Param("currentDate") LocalDateTime currentDate);
}

