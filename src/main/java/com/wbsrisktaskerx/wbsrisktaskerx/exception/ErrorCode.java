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
     * Error User
     */
    INVALID_USERNAME_OR_PASSWORD("invalid-username-or-password", HttpStatus.BAD_REQUEST),
    DURING_REGISTRATION_ERROR("during-registration-error", HttpStatus.BAD_REQUEST),
    SEND_EMAIL_ERROR("send-email-error", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST("email-exist", HttpStatus.BAD_REQUEST),
    USERNAME_EXIST("username-exist", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_FORMAT("invalid-image-format", HttpStatus.BAD_REQUEST),
    INVALID_OTP("invalid-otp", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("user-not-found", HttpStatus.BAD_REQUEST),
    NOT_FOUND("not-found", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE("account-not-active", HttpStatus.FORBIDDEN),


    INVALID_REQUEST("invalid-request", HttpStatus.BAD_REQUEST),
    REQUEST_NOT_FOUND("request-not-found", HttpStatus.BAD_REQUEST),



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
