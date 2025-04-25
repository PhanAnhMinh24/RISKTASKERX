package com.wbsrisktaskerx.wbsrisktaskerx.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum  ErrorCode {
    /*
     * Error System
     */
    SYSTEM_ERROR("system-error",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("unauthorized", HttpStatus.UNAUTHORIZED),

    /*
     * Error send email
     */
    EMAIL_NOT_FOUND("email-not-found", HttpStatus.FORBIDDEN),
    DATABASE_ERROR("database-error", HttpStatus.INTERNAL_SERVER_ERROR),

    /*
     * Error User
     */
    INVALID_USERNAME_OR_PASSWORD("invalid-username-or-password", HttpStatus.BAD_REQUEST),
    DURING_REGISTRATION_ERROR("during-registration-error", HttpStatus.BAD_REQUEST),
    SEND_EMAIL_ERROR("send-email-error", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("email-existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXIST("username-exist", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_FORMAT("invalid-image-format", HttpStatus.BAD_REQUEST),
    INVALID_OTP("invalid-otp", HttpStatus.BAD_REQUEST),
    OTP_NOT_VERIFIED("otp-not-verified", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("user-not-found", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND("customer-not-found", HttpStatus.BAD_REQUEST),
    ACCOUNT_ADMIN_NOT_FOUND("account-admin-not-found", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED(" account-already-activated", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED("Phone-number-is-existed", HttpStatus.BAD_REQUEST),

    NOT_FOUND("not-found", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE("account-not-active", HttpStatus.FORBIDDEN),
    PASSWORD_REQUIRED("password-required", HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCH("password-not-match", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT("password-too-short", HttpStatus.BAD_REQUEST),
    PASSWORD_NO_UPPERCASE("password-no-uppercase", HttpStatus.BAD_REQUEST),
    PASSWORD_NO_NUMBER("password-no-number", HttpStatus.BAD_REQUEST),
    PASSWORD_NO_SPECIAL_CHAR("password-no-special-char", HttpStatus.BAD_REQUEST),
    PASSWORD_CONTAINS_SPACE("password-contains-space", HttpStatus.BAD_REQUEST),
    PASSWORD_MATCHES_OLD_PASSWORD("password-matches-old-password", HttpStatus.BAD_REQUEST),
    INVALID_NUMERIC_VALUE("invalid-numeric-value", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL("invalid-email", HttpStatus.BAD_REQUEST),
    FIELD_IS_REQUIRED("This-field-is-required", HttpStatus.BAD_REQUEST),

    LICENSE_PLATE_EXISTS("license-plate-exists", HttpStatus.BAD_REQUEST),
    WARRANTY_HISTORY_NOT_FOUND("warranty-history-not-found", HttpStatus.BAD_REQUEST),

    ROLE_ALREADY_EXISTS("role-already-exists", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND("permission-not-found", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND("role-not-found", HttpStatus.BAD_REQUEST),
    ROLE_NAME_EXISTS("role-name-exists", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSION_DUPLICATE("role-permission-duplicate", HttpStatus.BAD_REQUEST),
    ROLE_INACTIVE_OR_UNAVAILABLE("role-inactive-or-unavailable", HttpStatus.BAD_REQUEST),
    ROLE_ID_REQUIRED("role-id-required", HttpStatus.BAD_REQUEST),
    ROLE_IN_USE_BY_ADMIN("role-in-use-by-admin", HttpStatus.BAD_REQUEST),
    INVALID_ROLE_NAME("invalid-role-name", HttpStatus.BAD_REQUEST),
    INVALID_VALID_NAME("invalid-valid-name", HttpStatus.BAD_REQUEST),

    INVALID_REQUEST("invalid-request", HttpStatus.BAD_REQUEST),
    REQUEST_NOT_FOUND("request-not-found", HttpStatus.BAD_REQUEST),

    AUTHORIZATION_HEADER_IS_MISSING_OR_INVALID("authorization-header-is-missing-or-invalid", HttpStatus.BAD_REQUEST),

    /*
     * Error Authentication
     */
    ERROR_JWT_IS_NOT_VALID("error-jwt-is-not-valid", HttpStatus.UNAUTHORIZED),
    ERROR_ANONYMOUS_AUTHENTICATION_TOKEN("error-anonymous-authentication-token", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
