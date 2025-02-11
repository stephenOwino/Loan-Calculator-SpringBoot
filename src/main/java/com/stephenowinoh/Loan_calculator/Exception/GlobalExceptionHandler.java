package com.stephenowinoh.Loan_calculator.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Handle CustomerNotFoundException
        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        // Handle LoanNotFoundException
        @ExceptionHandler(LoanNotFoundException.class)
        public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        // Handle BadRequestException
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // Handle any other generic exception
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGenericException(Exception ex) {
                return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(NameMismatchException.class)
        public ResponseEntity<String> handleNameMismatch(NameMismatchException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
}


