package com.foodcourt.court.domain.constants;

public class Constants {

    private Constants() {
        throw new IllegalStateException(ERROR_INSTANCE_UTILITY_CLASS);
    }
    public static final String ERROR_INSTANCE_UTILITY_CLASS = "Utility class";

    /*Description errors*/
    public static final String INVALID_NAME = "El nombre no es valido ";
    public static final String INVALID_PHONE_NUMBER = "El numero de telefono no es valido ";
    public static final String INVALID_ID_NUMBER = "El numero de identificacion no es valido ";
    public static final String OWNER_NO_FOUND = "El propietario no fue encontrado ";
    public static final String USER_NO_AUTHORIZED = "El id no corresponde a un propietario";
    public static final String USER_ROLE_NO_FOUND = "El rol es invalido";
    public static final String PRICE_NOT_ALLOWED = "El precio no es valido, debe ser mayor a 0";
    public static final String CATEGORY_NO_FOUND = "No se encontro categoria con el id ingresado";
    public static final String ORDER_NO_FOUND = "No se encontro pedido con el id ingresado";
    public static final String RESTAURANT_NO_FOUND = "Ningun restaurante coincide con el id ingresado";
    public static final String RESTAURANT_OWNER_NO_FOUND = "Ningun restaurante coincide con el id del propietario";
    public static final String PLATE_NO_FOUND = "Ningun plato coincide con el id ingresado";
    public static final String OWNER_NOT_ALLOWED = "El propietario no corresponde al restaurante ingresado";
    public static final String CLIENT_HAS_ORDERS_ACTIVE = "Ya cuenta con un pedido en proceso, debe finalizarlo para crear uno nuevo";
    public static final String PARAM_REQUIRED_NOT_FOUND = "Los parametros requeridos no pueden ser nulos ";
    public static final String ORDER_EMPTY = "El pedido debe contener al menos un plato";
    public static final String PLATE_NO_FOUND_IN_ORDER = "Algunos de los platos no se encontraron en el listado de el restaurante";
    public static final String ORDER_STATUS_NOT_FOUND = "El estado de la orden ingresado no corresponde a ninguno existente";
    public static final String EMPLOYEE_NOT_ALLOWED = "El empleado no tiene permisos para realizar esta accion";
    public static final String ORDER_STATUS_ACTION_NOT_ALLOWED = "El estado del pedido no permite realizar esta accion";
    public static final String CLIENT_PIN_CODE_INCORRECT = "El codigo del pedido es incorrecto";

    /*Params Name*/
    public static final String  PAGE_NAME = "page";
    public static final String  PAGE_SIZE_NAME = "pageSize";
    public static final String  CATEGORY_ID_PARAM_NAME = "categoryId";
    public static final String  ORDER_STATUS_PARAM_NAME = "orderStatus";


    /*Order status description*/
    public static final String  ORDER_STATUS_PENDING = "PENDIENTE";
    public static final String  ORDER_STATUS_CANCELED = "CANCELADO";
    public static final String  ORDER_STATUS_IN_PREPARATION = "EN_PREPARACION";
    public static final String  ORDER_STATUS_PREPARED = "LISTO";
    public static final String  ORDER_STATUS_DELIVERED = "ENTREGADO";

    /*Templates*/
    public static final String  NEGATIVE_VALUE_PARAM_TEMPLATE = "El parametro %s no puede tener valores negativos";

    /*Patterns properties*/
    public static final String NAME_RESTAURANT_PATTERN = "^(?!\\d+$)[\\p{L}\\d\\s]+$";
    public static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9]{1,12}$";
    public static final String ID_NUMBER_PATTERN = "^[0-9]+$";
    public static final Integer MIN_PRICE_ALLOW = 0;
    public static final Integer MIN_PAGE_ALLOW = 0;
    public static final Integer MAX_RANGE_CLIENT_PIN = 10;
    public static final Integer DIGIT_NUMBER_CLIENT_PIN = 6;
    public static final String EMPTY_STRING = "";

}
