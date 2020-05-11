package io.sskrishna.rest.exception;

import io.sskrishna.rest.response.RestResponse;
import lombok.Data;

@Data
public class RestException extends RuntimeException {
    private final RestResponse restResponse;

    public RestException(RestResponse restResponse) {
        this.restResponse = restResponse;
    }
}