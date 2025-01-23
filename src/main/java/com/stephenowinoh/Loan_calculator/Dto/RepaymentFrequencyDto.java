package com.stephenowinoh.Loan_calculator.Dto;


public class RepaymentFrequencyDto {
        private String value;
        private String displayName;

        // e.g., DAILY, WEEKLY, MONTHLY, ANNUALLY


        public RepaymentFrequencyDto() {
        }

        public RepaymentFrequencyDto(String value, String displayName) {
                this.value = value;
                this.displayName = displayName;
        }


        public String getValue() {
                return value;
        }

        public void setValue(String value) {
                this.value = value;
        }

        public String getDisplayName() {
                return displayName;
        }

        public void setDisplayName(String displayName) {
                this.displayName = displayName;
        }
}

