package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.infrastructure.shared.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = Constants.SUMMARY_CREATE_RESTAURANT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.STATUS_CODE_CREATED, description = Constants.SUMMARY_RESPONSE_CREATED_RESTAURANT, content = @Content),
            @ApiResponse(responseCode = Constants.STATUS_CODE_CONFLICT, description = Constants.SUMMARY_RESPONSE_CONFLICT_RESTAURANT, content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantHandler.create(restaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = Constants.SUMMARY_CREATE_RESTAURANT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.STATUS_CODE_CREATED, description = Constants.SUMMARY_RESPONSE_CREATED_RESTAURANT, content = @Content),
            @ApiResponse(responseCode = Constants.STATUS_CODE_CONFLICT, description = Constants.SUMMARY_RESPONSE_CONFLICT_RESTAURANT, content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<List<ListRestaurantsResponseDto>> getRestaurants( @RequestParam("pageSize") Integer pageSize,
                                                                            @RequestParam("page") Integer page) {
        List<ListRestaurantsResponseDto> listRestaurantsResponseDto = restaurantHandler.getRestaurants(pageSize, page);
        return new ResponseEntity<>(listRestaurantsResponseDto, HttpStatus.OK);
    }



}
