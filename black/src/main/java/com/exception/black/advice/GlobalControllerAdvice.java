package com.exception.black.advice;

import com.exception.black.dto.Error;
import com.exception.black.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice(basePackages = {"com.exception.black.controller"})
public class GlobalControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception error){
        System.out.println(error.getClass().getName());
        System.out.println(error.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(
            MethodArgumentNotValidException error,
            HttpServletRequest httpServletRequest
    ){
        List<Error> errorList = new ArrayList<>();
        BindingResult bindingResult = error.getBindingResult();
        bindingResult.getAllErrors().forEach(_error -> {
            FieldError field = (FieldError) _error;
            String fieldName = field.getField();
            String message = field.getDefaultMessage();
            String value = field.getRejectedValue().toString();
            System.out.println("fieldName: " + fieldName);
            System.out.println("message: " + message);
            System.out.println("value: " + value);

            Error err = new Error();
            err.setField(fieldName);
            err.setMessage(message);
            err.setInvalidValue(value);

            errorList.add(err);
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(
            ConstraintViolationException error,
            HttpServletRequest httpServletRequest
    ){

        List<Error> errorList = new ArrayList<>();

        error.getConstraintViolations().forEach(_error -> {
            Stream<Path.Node> stream = StreamSupport.stream(_error.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());
//            String field = _error.getLeafBean().toString();
            String field = list.get(list.size() - 1).getName();
            String message = _error.getMessage();
            String invalidValue = _error.getInvalidValue().toString();

            System.out.println(field);
            System.out.println(message);
            System.out.println(invalidValue);

            Error err = new Error();
            err.setField(field);
            err.setMessage(message);
            err.setInvalidValue(invalidValue);

            errorList.add(err);
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(
            MissingServletRequestParameterException error,
            HttpServletRequest httpServletRequest
    ){

        List<Error> errorList = new ArrayList<>();

        String fieldName = error.getParameterName();
        String fieldType = error.getParameterType();
        String invalidValue = error.getMessage();

        System.out.println(fieldName);
        System.out.println(fieldType);
        System.out.println(invalidValue);

        Error err = new Error();
        err.setField(fieldName);
        err.setMessage(error.getMessage());

        errorList.add(err);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
