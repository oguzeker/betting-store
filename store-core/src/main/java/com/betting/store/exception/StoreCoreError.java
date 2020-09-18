package com.betting.store.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum StoreCoreError {

    // BAD_REQUEST - 400 (validation errors, logical errors, etc.)
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST),

    // UNAUTHORIZED - 401 (Authentication problems)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),

    // UNPROCESSABLE_ENTITY - 422 (Cannot update record, record already exists, etc.)
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY),

    // INTERNAL_SERVER_ERROR - 500 (Technical problems)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    API_CONNECTION_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR),

    // NOT_IMPLEMENTED - 501 (Not supported functionality)
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED);

    @Getter
    private final HttpStatus httpStatus;

}
