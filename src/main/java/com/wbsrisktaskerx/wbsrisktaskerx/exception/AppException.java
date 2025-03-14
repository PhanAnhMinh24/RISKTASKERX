package com.wbsrisktaskerx.wbsrisktaskerx.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String customMessage;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.customMessage = errorCode.getMessage();
    }

    public AppException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}

