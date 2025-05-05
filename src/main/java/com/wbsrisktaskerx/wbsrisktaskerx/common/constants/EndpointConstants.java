package com.wbsrisktaskerx.wbsrisktaskerx.common.constants;

public class EndpointConstants {

    public static final String ACTUATOR = "/actuator";
    public static final String SWAGGER_ICO = "/favicon.ico";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_VER = "/v3";
    public static final String SWAGGER_API_DOCS = SWAGGER_VER + "/api-docs";
    public static final String SWAGGER_CONFIG = "/swagger-config";
    public static final String API = "/api";
    public static final String ADMIN = "/admin";

    // 🔹 Auth Endpoints
    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String ACTIVE = "/activate/{id}";

    // Profile
    public static final String PROFILE = API + "/profile";

    // Customers
    public static final String CUSTOMERS = "/customers";
    public static final String EXPORT = "/export";
    public static final String SEARCH_FILTER = "/search-and-filter";
    public static final String STATUS = "/status";
    public static final String HISTORY = "/history";
    public static final String PURCHASE = "/purchase";
    public static final String WARRANTY = "/warranty";

    public static final String ID = "/{id}";
    public static final String PAYMENTS_ID = "/{paymentsId}";

    //Roles
    public static final String ROLES = "/roles";
    public static final String ACTIVE_ROLES = "/active";

    //Permission
    public static final String PERMISSION = "/permissions";
    public static final String PERMISSION_ID = "/{id}";


}
