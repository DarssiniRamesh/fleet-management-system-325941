package com.example.javabackend.api.error;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Map;

@Schema(name = "ApiError", description = "Standard error response")
public class ApiError {

    @Schema(description = "Timestamp of the error", example = "2026-02-24T12:34:56Z")
    private Instant timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error code identifier", example = "VALIDATION_ERROR")
    private String code;

    @Schema(description = "Human-readable message", example = "Request validation failed")
    private String message;

    @Schema(description = "Request path", example = "/api/vehicles")
    private String path;

    @Schema(description = "Optional field-level validation errors", example = "{\"licensePlate\":\"must be at most 32 characters\"}")
    private Map<String, String> details;

    public ApiError() {}

    public ApiError(Instant timestamp, int status, String code, String message, String path, Map<String, String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
