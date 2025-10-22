package com.unimag.aeropuerto.API.Error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.OffsetDateTime;
import java.util.List;

public record ApiError(@JsonFormat(shape = JsonFormat.Shape.STRING) OffsetDateTime timestamp,
                       int statusCode, String error, String message,String path, List<fieldViolation> violations) {

    public static ApiError of(HttpStatus status, String message,String path, List<fieldViolation> violations) {
        return new ApiError(OffsetDateTime.now(), status.value(), status.getReasonPhrase(), message, path, violations);
    }

    public record fieldViolation(String field, String message){}

}