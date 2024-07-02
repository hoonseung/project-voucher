package com.study.projectvoucher.common.exception;


import java.time.LocalDateTime;
import java.util.UUID;

public record ErrorResponse(

        int statusCode,

        String message,

        LocalDateTime timeStamp,

        UUID traceId
) {


}
