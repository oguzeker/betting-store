package com.betting.store.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreCoreException extends RuntimeException {

    private StoreCoreError error = StoreCoreError.INTERNAL_SERVER_ERROR;
    private Object[] args;
    private String message;

    public StoreCoreException(StoreCoreError error, Object... args) {
        this.error = error;
        this.args = args;
    }

    public StoreCoreException(StoreCoreError error, Throwable cause, Object... args) {
        super(cause);
        this.error = error;
        this.args = args;
    }

}