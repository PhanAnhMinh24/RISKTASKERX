package com.wbsrisktaskerx.wbsrisktaskerx.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    EMAIL_NOT_FOUND("email-not-found", HttpStatus.FORBIDDEN),;
//    SYSTEM_ERROR("system-error", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
