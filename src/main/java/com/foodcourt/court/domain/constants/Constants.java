package com.foodcourt.court.domain.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException(ERROR_INSTANCE_UTILITY_CLASS);
    }

    public static final String ERROR_INSTANCE_UTILITY_CLASS = "Utility class";
    /*Descripcion Errores*/
    public static final String INVALID_NAME = "El nombre no es valido ";
    public static final String INVALID_PHONE_NUMBER = "El numero de telefono no es valido ";
    public static final String INVALID_ID_NUMBER = "El numero de identificacion no es valido ";
    public static final String OWNER_NO_FOUND = "El propietario no fue encontrado ";
    public static final String USER_NO_AUTHORIZED = "El id no corresponde a un propietario";
    public static final String USER_ROLE_NO_FOUND = "El rol es invalido";
    public static final String PRICE_NOT_ALLOWED = "El precio no es valido, debe ser mayor a 0";
    public static final String CATEGORY_NO_FOUND = "No se encontro categoria con el id ingresado";
    public static final String RESTAURANT_NO_FOUND = "Ningun restaurante coincide con el id ingresado";
    public static final String PLATE_NO_FOUND = "Ningun plato coincide con el id ingresado";
    public static final String OWNER_NOT_ALLOWED = "El propietario no corresponde al restaurante ingresado";

    /*Patrones propiedes*/
    public static final String NAME_RESTAURANT_PATTERN = "^(?!\\d+$)[\\p{L}\\d\\s]+$";
    public static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9]{1,12}$";
    public static final String ID_NUMBER_PATTERN = "^[0-9]+$";
    public static final Integer MIN_PRICE_ALLOW = 0;
}
