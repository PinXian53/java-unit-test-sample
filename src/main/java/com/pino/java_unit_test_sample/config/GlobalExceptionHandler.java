package com.pino.java_unit_test_sample.config;

import com.pino.java_unit_test_sample.exception.BadRequestException;
import com.pino.java_unit_test_sample.model.model.ExceptionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ExceptionDTO> handleBadRequestException(
        HttpServletRequest request,
        BadRequestException ex,
        HandlerMethod handlerMethod
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionDTO, httpStatus);
    }
}
