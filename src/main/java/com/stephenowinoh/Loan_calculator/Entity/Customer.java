package com.stephenowinoh.Loan_calculator.Entity;

import com.stephenowinoh.Loan_calculator.Role.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Loan> loans = new ArrayList<>();

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        // Default constructor
        public Customer() {}

        // Constructor with parameters for all fields
        public Customer(Long id, String firstName, String lastName, String username, String email, String password, LocalDateTime createdAt, List<Loan> loans, Role role) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.email = email;
                this.password = password;
                this.createdAt = createdAt;
                this.loans = loans;
                this.role = role;
        }

        // Constructor with customer ID (for some specific use cases)
        public Customer(Long customerId) {
                this.id = customerId;
        }

        // Getters and setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public List<Loan> getLoans() {
                return loans;
        }

        public void setLoans(List<Loan> loans) {
                this.loans = loans;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        // UserDetails interface methods
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(() -> "ROLE_" + role.name());
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
