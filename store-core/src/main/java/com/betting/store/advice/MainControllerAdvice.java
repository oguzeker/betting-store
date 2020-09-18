package com.betting.store.advice;


import com.betting.store.exception.StoreCoreError;
import com.betting.store.exception.StoreCoreException;
import com.betting.store.util.MessageSourceUtil;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class MainControllerAdvice {

    @ExceptionHandler(StoreCoreException.class)
    public final ResponseEntity<StoreCoreException> handleServiceException(
            StoreCoreException ex, WebRequest request) {
        return prepareResponse(ex);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<StoreCoreException> handleFeignException(FeignException ex,
                                                                         WebRequest request) {
        return prepareResponse(new StoreCoreException(StoreCoreError.API_CONNECTION_FAILURE, ex,
                ex.request().url()));
    }

    private static ResponseEntity<StoreCoreException> prepareResponse(StoreCoreException exception) {
        StoreCoreException ex = MessageSourceUtil.prepareException(exception);
        log.error("Controller-exception {}", kv("exception", ex));
        return ResponseEntity.status(exception.getError().getHttpStatus()).body(ex);
    }

}
