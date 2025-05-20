package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.handler.IPlateHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mngr/court")
@RequiredArgsConstructor
public class PlateRestController {

    private final IPlateHandler plateHandler;

    @Operation(summary = "Create a new plate in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plate created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information invalid", content = @Content)
    })
    @PostMapping("/plate")
    public ResponseEntity<Void> createPlate(@Valid @RequestBody CreatePlateRequestDto plateRequestDto) {
        plateHandler.create(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update a existand plate in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plate update successful", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information invalid", content = @Content)
    })
    @PatchMapping("/plate")
    public ResponseEntity<Void> updatePlate(@Valid @RequestBody UpdatePlateRequestDto plateRequestDto) {
        plateHandler.update(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
