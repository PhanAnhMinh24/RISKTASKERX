package com.wbsrisktaskerx.wbsrisktaskerx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}