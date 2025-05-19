package com.foodcourt.court.domain.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
    /*Descripcion Errores*/
    public static final String INVALID_NAME = "El nombre no es valido ";
    public static final String INVALID_PHONE_NUMBER = "El numero de telefono no es valido ";
    public static final String INVALID_ID_NUMBER = "El numero de identificacion no es valido ";
    public static final String OWNER_NO_FOUND = "El propietario no fue encontrado ";
    public static final String USER_NO_AUTHORIZED = "El id no corresponde a un propietario";
    public static final String USER_ROLE_NO_FOUND = "El rol es invalido";


    /*Patrones propiedes*/
    public static final String NAME_RESTAURANT_PATTERN = "^(?!\\d+$)[\\p{L}\\d\\s]+$";
    public static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9]{1,12}$";
    public static final String ID_NUMBER_PATTERN = "^[0-9]+$";

}
