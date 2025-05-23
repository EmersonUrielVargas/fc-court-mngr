package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.handler.IPlateHandler;
import com.foodcourt.court.infrastructure.security.service.AutheticationService;
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
@RequestMapping("/plate")
@RequiredArgsConstructor
public class PlateRestController {

    private final IPlateHandler plateHandler;
    private final AutheticationService autheticationService;

    @Operation(summary = "Create a new plate in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plate created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information invalid", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Void> createPlate(@Valid @RequestBody CreatePlateRequestDto plateRequestDto) {
        plateHandler.create(plateRequestDto, autheticationService.getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update a existand plate in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plate update successful", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information invalid", content = @Content)
    })
    @PatchMapping("")
    public ResponseEntity<Void> updatePlate(@Valid @RequestBody UpdatePlateRequestDto plateRequestDto) {
        plateHandler.update(plateRequestDto, autheticationService.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "update status to plate in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plate status update successful", content = @Content),
            @ApiResponse(responseCode = "409", description = "Information plate invalid", content = @Content)
    })
    @PatchMapping("/status")
    public ResponseEntity<Void> setStatusPlate(@Valid @RequestBody StatusPlateRequestDto plateRequestDto) {
        plateHandler.setStatus(plateRequestDto, autheticationService.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
