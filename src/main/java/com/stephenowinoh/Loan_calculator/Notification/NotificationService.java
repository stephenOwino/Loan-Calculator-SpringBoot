package com.stephenowinoh.Loan_calculator.Notification;

import com.stephenowinoh.Loan_calculator.Entity.Loan;
import com.stephenowinoh.Loan_calculator.Repository.LoanRepository;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;
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
                List<Loan> loans = loanRepository.findAll(); // Retrieve all loans

                for (Loan loan : loans) {
                        if (shouldSendInitialReminder(loan, now)) {
                                sendNotification(loan, "Your loan repayment is due on " + loan.getDueDate() + ". Please ensure that the payment is made on time.");
                        } else if (shouldSendDueDateReminder(loan, now)) {
                                sendNotification(loan, "Today is the due date for your loan repayment. Please make the payment to avoid any penalties.");
                        } else if (shouldSendFirstOverdueNotification(loan, now)) {
                                sendNotification(loan, "Your loan repayment was due and is now overdue. Please make the payment as soon as possible to avoid any penalties.");
                        } else if (shouldSendSecondOverdueNotification(loan, now)) {
                                sendNotification(loan, "This is a reminder that your loan repayment is overdue. Please make the payment to avoid further consequences.");
                        } else if (shouldSendFinalOverdueNotification(loan, now)) {
                                sendNotification(loan, "Your loan repayment is significantly overdue. This is the final notice to make the payment to avoid additional fees and impact on your credit score.");
                        }
                }
        }

        // Helper methods to check when to send notifications for different frequencies
        private boolean shouldSendInitialReminder(Loan loan, LocalDateTime now) {
                switch (loan.getRepaymentFrequency()) {
                        case WEEKLY:
                                return now.isEqual(loan.getDueDate().minusDays(3));
                        case MONTHLY:
                                return now.isEqual(loan.getDueDate().minusDays(7));
                        case YEARLY:
                                return now.isEqual(loan.getDueDate().minusDays(30));
                        default:
                                return false;
                }
        }

        private boolean shouldSendDueDateReminder(Loan loan, LocalDateTime now) {
                return now.isEqual(loan.getDueDate());
        }

        private boolean shouldSendFirstOverdueNotification(Loan loan, LocalDateTime now) {
                switch (loan.getRepaymentFrequency()) {
                        case WEEKLY:
                                return now.isEqual(loan.getDueDate().plusDays(1));
                        case MONTHLY:
                                return now.isEqual(loan.getDueDate().plusDays(1));
                        case YEARLY:
                                return now.isEqual(loan.getDueDate().plusDays(7));
                        default:
                                return false;
                }
        }

        private boolean shouldSendSecondOverdueNotification(Loan loan, LocalDateTime now) {
                switch (loan.getRepaymentFrequency()) {
                        case WEEKLY:
                                return now.isEqual(loan.getDueDate().plusDays(3));
                        case MONTHLY:
                                return now.isEqual(loan.getDueDate().plusDays(7));
                        case YEARLY:
                                return now.isEqual(loan.getDueDate().plusDays(14));
                        default:
                                return false;
                }
        }

        private boolean shouldSendFinalOverdueNotification(Loan loan, LocalDateTime now) {
                switch (loan.getRepaymentFrequency()) {
                        case WEEKLY:
                                return now.isEqual(loan.getDueDate().plusDays(7));
                        case MONTHLY:
                                return now.isEqual(loan.getDueDate().plusDays(14));
                        case YEARLY:
                                return now.isEqual(loan.getDueDate().plusDays(30));
                        default:
                                return false;
                }
        }

        // Send the notification
        private void sendNotification(Loan loan, String messageText) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(loan.getEmail());
                message.setSubject("Loan Repayment Notification");
                message.setText("Dear " + loan.getFullName() + ",\n\n" + messageText + "\n\nThank you.");

                mailSender.send(message);
        }
}
