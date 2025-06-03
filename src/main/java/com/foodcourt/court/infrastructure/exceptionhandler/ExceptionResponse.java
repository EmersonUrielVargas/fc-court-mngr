package com.foodcourt.court.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No se encontró datos para la petición"),
    GENERAL_ERROR("Se ha producido un error inesperado"),
    MISSING_REQUIRED_PARAMS_ERROR("Hay parametros requeridos faltantes"),
    INFORMATION_ERROR("Se ha producido un error con la informacion suministrada, revisa los datos e intentalo nuevamente");


    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}