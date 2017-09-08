package com.simercom.motohedge.modules.utils;

/**
 * Created by wmora on 8/05/17.
 */

public class Constants {

    public static final String SOAP_SERVICE_URL = "http://unreleased-reactors.000webhostapp.com/MotoHedge/main.php"; //motohedge.xml?wsdl";
    public static final String SOAP_NAMESPACE = "http://unreleased-reactors.000webhostapp.com/MotoHedge";
    public static final String MAIN_SOAP_ACTION = "http://unreleased-reactors.000webhostapp.com/MotoHedge/WSDLService.php";

    public static final String FAILED_LOGIN_MESSAGE = "Tuvimos problemas al iniciar sesión, por favor intentalo de nuevo ";

    public static final String KEY_LOGIN_SUCCESS = "100";
    public static final String KEY_SUCCESS_OPERATION = "true";

    public static final String SERVICE_KEY_NAME = "name";
    public static final String SERVICE_KEY_LASTNAME = "lastName";
    public static final String SERVICE_KEY_DOCTYPE = "docType";
    public static final String SERVICE_KEY_DOC_NUMBER = "docNumber";
    public static final String SERVICE_KEY_USER_EMAIL = "email";
    public static final String SERVICE_KEY_USER_CELLPHONE = "cellphone";
    public static final String SERVICE_KEY_USER_TEPELHONE = "telephone";
    public static final String SERVICE_KEY_SESSION_STATE = "sessionState";
    public static final String SERVICE_KEY_USER_PASSWORD = "password";

    public static final String KEY_SERVICE_TYPE = "serviceType";
    public static final String KEY_VEHICLE_TYPE = "vehicleType";
    public static final String KEY_SERVICE_ADDRESS = "serviceAddress";
    public static final String KEY_LATITUDE = "serviceLatitude";
    public static final String KEY_LONGITUDE = "serviceLongitude";
    public static final String KEY_USER_COMMENTS = "userComments";
    public static final String KEY_SERVICE_STATE = "serviceState";
    public static final String KEY_USER_CELLPHONE = "userCellphone";
    public static final String KEY_USER_EMAIL = "userEmail";
    public static final String KEY_SERVICE_CONSECUTIVE = "consecutive";
    public static final String KEY_SERVICE_DATE = "date";

    public static final String SOAP_LOGIN_METHOD = "signIn";
    public static final String SOAP_LOGOUT_METHOD = "signOut";
    public static final String SOAP_REGISTER_METHOD ="register";
    public static final String SOAP_CHECK_SESSION_METHOD = "verifySession";
    public static final String SOAP_SERVICE_REQUEST_METHOD = "serviceRequest";
    public static final String SOAP_SERVICE_LIST_METHOD = "getServices";

    public static final String PARAMS_TAG = "params";

    public static final String SESSION_STATE_SIGNED_IN = "1";
    public static final int PROCESS_LOGOUT = 1;
    public static final int PROCESS_CHECK_SESSION = 2;

    public static final int LOCATION_PERMISSION = 10001;

    public static final String PREFERENCES = "MotoHedgePreferences";
    public static final String USER = "MotoHedgeUser";

    public static String getKindOfService(int index){
        String kindOfService = "";
        switch(index){
            case 1:
                kindOfService = "Carro taller";
                break;
            case 2:
                kindOfService = "Grúa";
                break;
            case 3:
                kindOfService = "Asistencia legal";
                break;
            default:
                kindOfService = "desconocido";
                break;
        }

        return kindOfService;
    }

}
