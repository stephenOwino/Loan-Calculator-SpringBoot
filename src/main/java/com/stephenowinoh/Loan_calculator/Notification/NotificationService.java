package com.stephenowinoh.Loan_calculator.Notification;


import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Repository.LoanRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

        private final LoanRepository loanRepository;
        private final JavaMailSender mailSender;

        public NotificationService(LoanRepository loanRepository, JavaMailSender mailSender) {
                this.loanRepository = loanRepository;
                this.mailSender = mailSender;
        }

        @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
        public void sendRepaymentNotifications() {
                LocalDateTime now = LocalDateTime.now();
                List<Loan> dueLoans = loanRepository.findByDueDateBefore(now.plusDays(1)); // Loans due within the next day

                for (Loan loan : dueLoans) {
                        sendNotification(loan);
                }
        }

        private void sendNotification(Loan loan) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(loan.getEmail());
                message.setSubject("Loan Repayment Due Reminder");
                message.setText("Dear " + loan.getFullName() + ",\n\n" +
                        "This is a reminder that your loan repayment is due on " + loan.getDueDate() + ".\n" +
                        "Please ensure that the repayment is made on time to avoid any penalties.\n\n" +
                        "Thank you.");

                mailSender.send(message);
        }
}

