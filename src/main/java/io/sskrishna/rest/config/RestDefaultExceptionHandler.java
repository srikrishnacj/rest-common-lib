package io.sskrishna.rest.config;

import io.sskrishna.rest.exception.RestException;
import io.sskrishna.rest.exception.container.RestError;
import io.sskrishna.rest.exception.container.RestErrorBuilder;
import io.sskrishna.rest.response.RestResponse;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public abstract class RestDefaultExceptionHandler {
    private RestErrorBuilder errorBuilder;

    public RestDefaultExceptionHandler(RestErrorBuilder errorBuilder) {
        this.errorBuilder = errorBuilder;
    }

    @ExceptionHandler({RestException.class})
    public final ResponseEntity handleRestException(Exception ex, WebRequest request) {
        RestException exception = (RestException) ex;
        RestResponse restResponse = exception.getRestResponse();
        return new ResponseEntity(restResponse, new HttpHeaders(), HttpStatus.valueOf(restResponse.getStatus()));
    }

    @ExceptionHandler({StaleObjectStateException.class})
    public final ResponseEntity handleStaleObjectStateException(Exception ex, WebRequest request) {
        RestError restError = this.errorBuilder.restError();
        restError.withCode("document.modified");
        return new ResponseEntity(restError, new HttpHeaders(), HttpStatus.valueOf(422));
    }
}
