package com.example.javabackend.api.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralized exception-to-HTTP response mapper.
 *
 * <p>Contract:
 * <ul>
 *   <li>All API errors are returned as {@link ApiError}</li>
 *   <li>Validation errors return code=VALIDATION_ERROR and details map</li>
 *   <li>Not-found returns code=NOT_FOUND</li>
 *   <li>Conflicts return code=CONFLICT</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiError> build(
            HttpStatus status, String code, String message, HttpServletRequest request, Map<String, String> details) {
        ApiError error =
                new ApiError(Instant.now(), status.value(), code, message, request.getRequestURI(), details);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("NOT_FOUND: resourceType={} identifier={} path={}", ex.getResourceType(), ex.getIdentifier(), request.getRequestURI());
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), request, null);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ResourceConflictException ex, HttpServletRequest request) {
        log.warn(
                "CONFLICT: resourceType={} field={} path={} message={}",
                ex.getResourceType(),
                ex.getConflictField(),
                request.getRequestURI(),
                ex.getMessage());
        Map<String, String> details = new LinkedHashMap<>();
        details.put(ex.getConflictField(), ex.getMessage());
        return build(HttpStatus.CONFLICT, "CONFLICT", ex.getMessage(), request, details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleBodyValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> details = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.debug("VALIDATION_ERROR: path={} details={}", request.getRequestURI(), details);
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolations(ConstraintViolationException ex, HttpServletRequest request) {
        log.debug("VALIDATION_ERROR: path={} message={}", request.getRequestURI(), ex.getMessage());
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed", request, null);
    }

    /**
     * Fallback for DB-level constraint violations that may leak through (e.g., unique constraints).
     * We still keep the response shape consistent.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("DATA_INTEGRITY: path={} message={}", request.getRequestURI(), ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.CONFLICT, "CONFLICT", "Request conflicts with existing data", request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex, HttpServletRequest request) {
        log.error("INTERNAL_ERROR: path={} message={}", request.getRequestURI(), ex.getMessage(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error", request, null);
    }
}
