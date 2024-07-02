package com.study.projectvoucher.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

@RestControllerAdvice
public class ApiControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e){
        log.error(Arrays.toString(e.getStackTrace()));
        return createResponse.apply(HttpStatus.BAD_REQUEST, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handleIllegalStateException(final IllegalStateException e){
        log.error(Arrays.toString(e.getStackTrace()));
        return createResponse.apply(HttpStatus.BAD_REQUEST, e);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleIllegalStateException(final Exception e){
        log.error(Arrays.toString(e.getStackTrace()));
        return createResponse.apply(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }


    private BiFunction<HttpStatus, Exception, ErrorResponse> createResponse = ((httpStatus, e) -> {
        var traceId = UUID.randomUUID();
        log.error("traceId : {}", traceId);
       return new ErrorResponse(httpStatus.value(), e.getMessage(), LocalDateTime.now(), traceId);
    });


}
