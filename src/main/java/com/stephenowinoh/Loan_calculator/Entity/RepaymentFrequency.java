package com.stephenowinoh.Loan_calculator.Entity;

public enum RepaymentFrequency {
        MONTHLY("Monthly"),
        WEEKLY("Weekly"),
        YEARLY("Yearly");

        private String displayName;

        RepaymentFrequency(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }

        public static RepaymentFrequency fromDisplayName(String displayName) {
                for (RepaymentFrequency frequency : values()) {
                        if (frequency.getDisplayName().equalsIgnoreCase(displayName)) {
                                return frequency;
                        }
                }
                throw new IllegalArgumentException("Invalid repayment frequency: " + displayName);
        }
}
