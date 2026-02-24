package com.example.javabackend.api.controller;

import com.example.javabackend.api.dto.vehicle.VehicleCreateRequest;
import com.example.javabackend.api.dto.vehicle.VehicleResponse;
import com.example.javabackend.api.dto.vehicle.VehicleUpdateRequest;
import com.example.javabackend.api.error.ApiError;
import com.example.javabackend.api.mapper.VehicleMapper;
import com.example.javabackend.domain.Vehicle;
import com.example.javabackend.service.VehicleService;
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
 * Vehicle API endpoints.
 *
 * <p>Entrypoints (boundary layer) responsibilities:
 * <ul>
 *   <li>Validate request DTOs</li>
 *   <li>Invoke service flows</li>
 *   <li>Return DTOs</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Vehicle management endpoints")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List vehicles",
            description = "Returns all vehicles.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "List of vehicles",
                        content =
                                @Content(
                                        array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class))))
            })
    public List<VehicleResponse> listVehicles() {
        /** List vehicles. */
        return vehicleService.listVehicles().stream().map(VehicleMapper::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(
            summary = "Get vehicle",
            description = "Returns a vehicle by id.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Vehicle",
                        content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public VehicleResponse getVehicle(@PathVariable("id") long id) {
        /** Get vehicle by id. */
        Vehicle v = vehicleService.getVehicle(id);
        return VehicleMapper.toResponse(v);
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create vehicle",
            description = "Creates a vehicle. Uniqueness is enforced for vin and licensePlate when provided.",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created vehicle",
                        content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = @Content(schema = @Schema(implementation = ApiError.class))),
                @ApiResponse(
                        responseCode = "409",
                        description = "Conflict (uniqueness violation)",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public VehicleResponse createVehicle(@Valid @RequestBody VehicleCreateRequest request) {
        /** Create vehicle. */
        return VehicleMapper.toResponse(vehicleService.createVehicle(request));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(
            summary = "Update vehicle",
            description = "Updates a vehicle by id. Fields are optional; omitted fields are unchanged.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Updated vehicle",
                        content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
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
    public VehicleResponse updateVehicle(@PathVariable("id") long id, @Valid @RequestBody VehicleUpdateRequest request) {
        /** Update vehicle. */
        return VehicleMapper.toResponse(vehicleService.updateVehicle(id, request));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete vehicle",
            description = "Deletes a vehicle by id.",
            responses = {
                @ApiResponse(responseCode = "204", description = "Deleted"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public void deleteVehicle(@PathVariable("id") long id) {
        /** Delete vehicle. */
        vehicleService.deleteVehicle(id);
    }
}
