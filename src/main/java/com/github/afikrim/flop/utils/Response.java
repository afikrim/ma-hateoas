package com.github.afikrim.flop.utils;

import org.springframework.hateoas.RepresentationModel;

public class Response<T> extends RepresentationModel<Response<T>> {

    private Boolean success = true;
    private ResponseCode responseCode = ResponseCode.HTTP_OK;
    private String message;
    private T data;

    public Response(Boolean success, ResponseCode responseCode, String message, T data) {
        this.success = success;
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}
