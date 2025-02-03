package com.stephenowinoh.Loan_calculator.TimeSerializationDecerialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

        @Bean
        public ObjectMapper objectMapper() {
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
                module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
                mapper.registerModule(module);
                return mapper;
        }
}

