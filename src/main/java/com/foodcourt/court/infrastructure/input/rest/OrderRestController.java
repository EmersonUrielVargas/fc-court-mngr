package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.CreateOrderRequestDto;
import com.foodcourt.court.application.dto.request.UpdateStatusOrderRequestDto;
import com.foodcourt.court.application.handler.IOrderHandler;
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
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = GeneralConstants.SUMMARY_CREATE_ORDER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CREATED, description = GeneralConstants.SUMMARY_RESPONSE_CREATED_ORDER, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_ORDER, content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderRequestDto orderRequestDto) {
        orderHandler.create(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = GeneralConstants.SUMMARY_CREATE_ORDER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_OK, description = GeneralConstants.SUMMARY_RESPONSE_CREATED_ORDER, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_ORDER, content = @Content)
    })
    @PatchMapping("/status")
    public ResponseEntity<Void> updateStatusOrder(@Valid @RequestBody UpdateStatusOrderRequestDto updateStatusOrderRequestDto) {
        orderHandler.updateStatusOrder(updateStatusOrderRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
