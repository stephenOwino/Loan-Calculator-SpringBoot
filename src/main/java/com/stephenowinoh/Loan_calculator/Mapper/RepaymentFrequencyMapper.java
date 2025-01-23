package com.stephenowinoh.Loan_calculator.Mapper;

import com.stephenowinoh.Loan_calculator.Dto.RepaymentFrequencyDto;
import com.stephenowinoh.Loan_calculator.Entity.RepaymentFrequency;

public class RepaymentFrequencyMapper {

        // Map RepaymentFrequency enum to RepaymentFrequencyDto
        public static RepaymentFrequencyDto toDto(RepaymentFrequency repaymentFrequency) {
                String displayName = capitalizeFirstLetter(repaymentFrequency.name().toLowerCase()); // Capitalize the first letter
                return new RepaymentFrequencyDto(
                        repaymentFrequency.name(),
                        displayName
                );
        }

        // Map RepaymentFrequencyDto to RepaymentFrequency enum
        public static RepaymentFrequency toEntity(RepaymentFrequencyDto repaymentFrequencyDto) {
                return RepaymentFrequency.valueOf(repaymentFrequencyDto.getValue());
        }

        // Utility method to capitalize the first letter of each word (e.g., "DAILY" -> "Daily")
        private static String capitalizeFirstLetter(String str) {
                if (str == null || str.isEmpty()) {
                        return str;
                }
                return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
}
