package com.foodcourt.court.infrastructure.out.rest.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.court.domain.exception.DomainException;
import com.foodcourt.court.infrastructure.exception.NoDataFoundException;
import com.foodcourt.court.infrastructure.exceptionhandler.ExceptionResponse;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ControllerFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        String message = getMessageException(response);
        String defaultMessage = Objects.nonNull(message)? message : ExceptionResponse.INFORMATION_ERROR.getMessage();
        return switch (String.valueOf(response.status())) {
            case GeneralConstants.STATUS_CODE_CONFLICT -> new DomainException(defaultMessage);
            case GeneralConstants.STATUS_CODE_NOT_FOUND ->
                    new NoDataFoundException(Objects.nonNull(message) ? message : ExceptionResponse.NO_DATA_FOUND.getMessage());
            default -> new Exception(defaultMessage);
        };
    }

    private String getMessageException(Response response){
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(body).path(GeneralConstants.DEFAULT_PARAM_EXCEPTION_RESPONSE).asText();
        } catch (IOException e) {
            return null;
        }
    }
}
