package com.arogyam.health.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class LoginRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // Default constructor
    public LoginRequestDto() {
    }

    // All arguments constructor
    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Builder pattern implementation
    public static LoginRequestDtoBuilder builder() {
        return new LoginRequestDtoBuilder();
    }

    // Builder class
    public static class LoginRequestDtoBuilder {
        private String username;
        private String password;

        public LoginRequestDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public LoginRequestDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequestDto build() {
            return new LoginRequestDto(username, password);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LoginRequestDto that = (LoginRequestDto) obj;

        if (!Objects.equals(username, that.username)) return false;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    // toString() method (password masked for security)
    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                '}';
    }
}