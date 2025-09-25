package com.arogyam.health.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String error;

    // Default constructor
    public ApiResponseDto() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with essential fields
    public ApiResponseDto(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // All arguments constructor
    public ApiResponseDto(boolean success, String message, T data, String error) {
        this(success, message, data);
        this.error = error;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // Builder pattern implementation (following pattern from other DTOs)
    public static <T> ApiResponseDtoBuilder<T> builder() {
        return new ApiResponseDtoBuilder<>();
    }

    // Builder class
    public static class ApiResponseDtoBuilder<T> {
        private boolean success;
        private String message;
        private T data;
        private String error;

        public ApiResponseDtoBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseDtoBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseDtoBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseDtoBuilder<T> error(String error) {
            this.error = error;
            return this;
        }

        public ApiResponseDto<T> build() {
            return new ApiResponseDto<>(success, message, data, error);
        }
    }

    // Success response factory methods
    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>(true, message, data);
    }

    public static <T> ApiResponseDto<T> success(String message) {
        return new ApiResponseDto<>(true, message, null);
    }

    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>(true, "Success", data);
    }

    // Error response factory methods
    public static <T> ApiResponseDto<T> error(String message) {
        return new ApiResponseDto<>(false, message, null);
    }

    public static <T> ApiResponseDto<T> error(String message, String errorDetails) {
        return new ApiResponseDto<>(false, message, null, errorDetails);
    }

    // Specialized error factory methods
    public static <T> ApiResponseDto<T> validationError(String message, T validationErrors) {
        return new ApiResponseDto<>(false, message, validationErrors, "Validation failed");
    }

    public static <T> ApiResponseDto<T> notFound(String message) {
        return new ApiResponseDto<>(false, message, null, "Resource not found");
    }

    public static <T> ApiResponseDto<T> unauthorized(String message) {
        return new ApiResponseDto<>(false, message, null, "Unauthorized access");
    }

    public static <T> ApiResponseDto<T> serverError(String message) {
        return new ApiResponseDto<>(false, message, null, "Internal server error");
    }

    public static <T> ApiResponseDto<T> badRequest(String message) {
        return new ApiResponseDto<>(false, message, null, "Bad request");
    }

    // Utility methods
    public boolean hasData() {
        return data != null;
    }

    public boolean hasError() {
        return error != null && !error.trim().isEmpty();
    }

    public boolean isFailure() {
        return !success;
    }

    // equals() method using modern approach
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ApiResponseDto<?> that = (ApiResponseDto<?>) obj;

        if (success != that.success) return false;
        if (!Objects.equals(message, that.message)) return false;
        if (!Objects.equals(data, that.data)) return false;
        if (!Objects.equals(timestamp, that.timestamp)) return false;
        return Objects.equals(error, that.error);
    }

    // hashCode() method using modern approach
    @Override
    public int hashCode() {
        return Objects.hash(success, message, data, timestamp, error);
    }

    // toString() method
    @Override
    public String toString() {
        return "ApiResponseDto{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                ", error='" + error + '\'' +
                '}';
    }
}