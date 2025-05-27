package com.foodcourt.court.infrastructure.input.rest;

import com.foodcourt.court.application.dto.request.RestaurantRequestDto;
import com.foodcourt.court.application.dto.response.ListRestaurantsResponseDto;
import com.foodcourt.court.application.handler.IRestaurantHandler;
import com.foodcourt.court.domain.constants.Constants;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = GeneralConstants.SUMMARY_CREATE_RESTAURANT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CREATED, description = GeneralConstants.SUMMARY_RESPONSE_CREATED_RESTAURANT, content = @Content),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CONFLICT, description = GeneralConstants.SUMMARY_RESPONSE_CONFLICT_RESTAURANT, content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<Void> createRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantHandler.create(restaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = GeneralConstants.SUMMARY_GET_RESTAURANT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_CREATED, description = GeneralConstants.SUMMARY_RESPONSE_OK_GET_RESTAURANT,
                    content = @Content(mediaType = GeneralConstants.MEDIA_TYPE_JSON,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ListRestaurantsResponseDto.class)
                            )
                    )),
            @ApiResponse(responseCode = GeneralConstants.STATUS_CODE_BAD_REQUEST, description = GeneralConstants.SUMMARY_RESPONSE_BAD_REQUEST_GET_RESTAURANT, content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<List<ListRestaurantsResponseDto>> getRestaurants( @RequestParam(Constants.PAGE_SIZE_NAME) Integer pageSize,
                                                                            @RequestParam(Constants.PAGE_NAME) Integer page) {
        List<ListRestaurantsResponseDto> listRestaurantsResponseDto = restaurantHandler.getRestaurants(pageSize, page);
        return new ResponseEntity<>(listRestaurantsResponseDto, HttpStatus.OK);
    }



}
