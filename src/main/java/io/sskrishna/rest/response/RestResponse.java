package io.sskrishna.rest.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Data
public class RestResponse {
    private long timestamp = System.currentTimeMillis();
    private int status;
    private Object data;
    private int length;


    public static RestResponse initWith(Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.data(data);
        return restResponse;
    }

    public static RestResponse init() {
        RestResponse restResponse = new RestResponse();
        return restResponse;
    }

    public static RestResponse notFound() {
        return init().status(404);
    }

    public static RestResponse created(Object data) {
        return initWith(data).status(201);
    }

    public static RestResponse success(Object data) {
        return initWith(data).status(200);
    }

    public RestResponse data(Object data) {
        this.data = data;
        if(data instanceof Collection){
            Collection collection = (Collection) data;
            this.length = collection.size();
        }
        return this;
    }

    public RestResponse status(int status) {
        this.status = status;
        return this;
    }

    public ResponseEntity responseEntity() {
        ResponseEntity responseEntity;
        if (data == null) {
            responseEntity = new ResponseEntity(HttpStatus.valueOf(this.status));
        } else {
            responseEntity = new ResponseEntity(this, HttpStatus.valueOf(this.status));
        }
        return responseEntity;
    }
}
