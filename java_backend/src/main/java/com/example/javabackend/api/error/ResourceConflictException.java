package com.example.javabackend.api.error;

/**
 * Exception thrown when a request would violate a uniqueness constraint or otherwise conflicts with existing state.
 */
public class ResourceConflictException extends RuntimeException {

    private final String resourceType;
    private final String conflictField;

    public ResourceConflictException(String resourceType, String conflictField, String message) {
        super(message);
        this.resourceType = resourceType;
        this.conflictField = conflictField;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getConflictField() {
        return conflictField;
    }
}
