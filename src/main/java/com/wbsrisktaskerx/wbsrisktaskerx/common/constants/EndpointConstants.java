package com.wbsrisktaskerx.wbsrisktaskerx.common.constants;

public class EndpointConstants {

    public static final String EMAIL = "/email";
    public static final String SEND = "/send";

    public static final String ACTUATOR = "/actuator";
    public static final String SWAGGER_ICO = "/favicon.ico";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_VER = "/v3";
    public static final String SWAGGER_API_DOCS = SWAGGER_VER + "/api-docs";
    public static final String SWAGGER_CONFIG = "/swagger-config";

    // 🔹 Auth Endpoints
    public static final String AUTH = "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    // 🔹 OTP Endpoints
    public static final String OTP = "/otp";
    public static final String OTP_SEND = "/send-code";
    public static final String OTP_VERIFY = "/verify-otp";// Xác minh OTP
    public static final String OTP_RESET_PASSWORD = "/reset-password";// Xác minh OTP

    //Profile Endpoints
    public static final String PROFILE = "/profile";
    public static final String PROFILE_GET = "/{userId}";  // Lấy thông tin hồ sơ
    public static final String PROFILE_EDIT = "/{userId}"; // Cập nhật hồ sơ

}
