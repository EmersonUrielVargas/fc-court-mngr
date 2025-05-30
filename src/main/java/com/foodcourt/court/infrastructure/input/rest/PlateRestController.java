package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.CreatePlateRequestDto;
import com.foodcourt.court.application.dto.request.StatusPlateRequestDto;
import com.foodcourt.court.application.dto.request.UpdatePlateRequestDto;
import com.foodcourt.court.application.handler.IPlateHandler;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
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
@RequestMapping("/v1/plate")
@RequiredArgsConstructor
public class PlateRestController {

    private final IPlateHandler plateHandler;

    @Operation(summary = GeneralConstants.SUMMARY_CREATE_PLATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CREATED, description = GeneralConstants.SUMMARY_RESPONSE_CREATED_PLATE, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_PLATE, content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Void> createPlate(@Valid @RequestBody CreatePlateRequestDto plateRequestDto) {
        plateHandler.create(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = GeneralConstants.SUMMARY_UPDATE_PLATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_OK, description = GeneralConstants.SUMMARY_RESPONSE_OK_UPDATE_PLATE, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_PLATE, content = @Content)
    })
    @PatchMapping("")
    public ResponseEntity<Void> updatePlate(@Valid @RequestBody UpdatePlateRequestDto plateRequestDto) {
        plateHandler.update(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = GeneralConstants.SUMMARY_SET_ACTIVE_PLATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_OK, description = GeneralConstants.SUMMARY_RESPONSE_OK_SET_ACTIVE_PLATE, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_PLATE, content = @Content)
    })
    @PatchMapping("/status")
    public ResponseEntity<Void> setStatusPlate(@Valid @RequestBody StatusPlateRequestDto plateRequestDto) {
        plateHandler.setStatus(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
