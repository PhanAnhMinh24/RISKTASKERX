package com.wbsrisktaskerx.wbsrisktaskerx.common.constants;

public class EndpointConstants {

    public static final String ACTUATOR = "/actuator";
    public static final String SWAGGER_ICO = "/favicon.ico";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_VER = "/v3";
    public static final String SWAGGER_API_DOCS = SWAGGER_VER + "/api-docs";
    public static final String SWAGGER_CONFIG = "/swagger-config";
    public static final String API = "/api";

    // 🔹 Auth Endpoints
    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String CHANGE_PASSWORD = "/change-password";

    // Profile
    public static final String PROFILE = API + "/profile";

    // Customers
    public static final String CUSTOMERS = "/customers";
    public static final String EXPORT = "/export";
    public static final String LIST_CUSTOMERS = "/list-customers";
    public static final String SEARCH_FILTER = "/search-and-filter";
    public static final String FULL_SEARCH_FILTER = "/full-search-and-filter";
    public static final String STATUS = "/status";
    public static final String CUSTOMER_DETAILS = "/customer-details";
    public static final String HISTORY = "/history";

    public static final String ID = "/{id}";
}
