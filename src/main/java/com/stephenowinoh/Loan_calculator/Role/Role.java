package com.stephenowinoh.Loan_calculator.Role;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
        CUSTOMER,
        ADMIN;

        // This method will ensure the enum is serialized as a string
        @JsonValue
        public String getRoleString() {
                return this.name();
        }
}
