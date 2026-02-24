package com.example.javabackend.api.controller;

import com.example.javabackend.api.dto.driver.DriverCreateRequest;
import com.example.javabackend.api.dto.driver.DriverResponse;
import com.example.javabackend.api.dto.driver.DriverUpdateRequest;
import com.example.javabackend.api.error.ApiError;
import com.example.javabackend.api.mapper.DriverMapper;
import com.example.javabackend.domain.Driver;
import com.example.javabackend.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Driver API endpoints.
 */
@RestController
@RequestMapping("/api/drivers")
@Tag(name = "Drivers", description = "Driver management endpoints")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List drivers",
            description = "Returns all drivers.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "List of drivers",
                        content =
                                @Content(
                                        array = @ArraySchema(schema = @Schema(implementation = DriverResponse.class))))
            })
    public List<DriverResponse> listDrivers() {
        /** List drivers. */
        return driverService.listDrivers().stream().map(DriverMapper::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(
            summary = "Get driver",
            description = "Returns a driver by id.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Driver",
                        content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public DriverResponse getDriver(@PathVariable("id") long id) {
        /** Get driver by id. */
        Driver d = driverService.getDriver(id);
        return DriverMapper.toResponse(d);
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create driver",
            description = "Creates a driver. Uniqueness is enforced for email and licenseNumber when provided.",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created driver",
                        content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = @Content(schema = @Schema(implementation = ApiError.class))),
                @ApiResponse(
                        responseCode = "409",
                        description = "Conflict (uniqueness violation)",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public DriverResponse createDriver(@Valid @RequestBody DriverCreateRequest request) {
        /** Create driver. */
        return DriverMapper.toResponse(driverService.createDriver(request));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(
            summary = "Update driver",
            description = "Updates a driver by id. Fields are optional; omitted fields are unchanged.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Updated driver",
                        content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = @Content(schema = @Schema(implementation = ApiError.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content(schema = @Schema(implementation = ApiError.class))),
                @ApiResponse(
                        responseCode = "409",
                        description = "Conflict (uniqueness violation)",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public DriverResponse updateDriver(@PathVariable("id") long id, @Valid @RequestBody DriverUpdateRequest request) {
        /** Update driver. */
        return DriverMapper.toResponse(driverService.updateDriver(id, request));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete driver",
            description = "Deletes a driver by id.",
            responses = {
                @ApiResponse(responseCode = "204", description = "Deleted"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public void deleteDriver(@PathVariable("id") long id) {
        /** Delete driver. */
        driverService.deleteDriver(id);
    }
}
