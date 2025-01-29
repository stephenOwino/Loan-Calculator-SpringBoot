package com.stephenowinoh.Loan_calculator.Entity;

public enum RepaymentFrequency {
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        YEARLY("Yearly");

        private final String displayName;

        RepaymentFrequency(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }

        // Optional: Method to return enum by display name if needed
        public static RepaymentFrequency fromDisplayName(String displayName) {
                for (RepaymentFrequency frequency : RepaymentFrequency.values()) {
                        if (frequency.getDisplayName().equalsIgnoreCase(displayName)) {
                                return frequency;
                        }
                }
                throw new IllegalArgumentException("Unknown frequency: " + displayName);
        }
}
