package com.github.afikrim.flop.utils;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required'
     * request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<Object> response = new Response<>(false, ResponseCode.BAD_REQUEST, ex.getMessage(), null);

        return ResponseEntity.status(status).headers(headers).body(response);
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
     * invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<Object> response = new Response<>(false, ResponseCode.UNSUPPORTED_MEDIA_TYPE, ex.getMessage(), null);

        return ResponseEntity.status(status).headers(headers).body(response);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more
     * detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        Response<Object> response = new Response<>(false, ResponseCode.NOT_FOUND, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is
     * malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Malformed JSON request";
        Response<Object> response = new Response<>(false, ResponseCode.BAD_REQUEST, message, null);

        return ResponseEntity.status(status).headers(headers).body(response);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Error writing JSON output";
        Response<Object> response = new Response<>(false, ResponseCode.BAD_REQUEST, message, null);

        return ResponseEntity.status(status).headers(headers).body(response);
    }

    /**
     * Handle HttpRequestMethodNotSupportedException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Method not allowed";
        Response<Object> response = new Response<>(false, ResponseCode.METHOD_NOT_ALLOWED, message, null);

        return ResponseEntity.status(status).headers(headers).body(response);
    }

}
