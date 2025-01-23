package com.stephenowinoh.Loan_calculator.Exception;


public class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
                super(message);
        }
}
