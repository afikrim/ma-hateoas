package com.github.afikrim.flop.utils;

public class Response<T> {

    private Boolean status = true;
    private ResponseCode responseCode = ResponseCode.HTTP_OK;
    private String message;
    private T data;

    public Response(Boolean status, ResponseCode responseCode, String message, T data) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
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
