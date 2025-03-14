package com.wbsrisktaskerx.wbsrisktaskerx.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ErrorResponse {
    String message;
    HttpStatus status;

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getStatus());
    }
}
