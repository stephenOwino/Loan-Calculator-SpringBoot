package com.stephenowinoh.Loan_calculator.Entity;

public enum RepaymentFrequency {
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        YEARLY("Yearly");

        private String displayName;

        RepaymentFrequency(String displayName) {
                this.displayName = displayName;
        }

        public String getDisplayName() {
                return displayName;
        }

        public static RepaymentFrequency fromDisplayName(String name) {
                for (RepaymentFrequency frequency : values()) {
                        if (frequency.name().equalsIgnoreCase(name)) {
                                return frequency;
                        }
                }
                return null;  // or handle this case appropriately
        }
}