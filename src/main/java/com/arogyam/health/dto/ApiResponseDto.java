package com.arogyam.health.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String error;

    public ApiResponseDto() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDto(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Success responses
    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>(true, message, data);
    }

    public static <T> ApiResponseDto<T> success(String message) {
        return new ApiResponseDto<>(true, message, null);
    }

    // Error responses
    public static <T> ApiResponseDto<T> error(String message) {
        return new ApiResponseDto<>(false, message, null);
    }

    public static <T> ApiResponseDto<T> error(String message, String error) {
        ApiResponseDto<T> response = new ApiResponseDto<>(false, message, null);
        response.setError(error);
        return response;
    }

    // Convenience method for validation errors
    public static <T> ApiResponseDto<T> validationError(String message, T validationErrors) {
        ApiResponseDto<T> response = new ApiResponseDto<>(false, message, validationErrors);
        response.setError("Validation failed");
        return response;
    }
}