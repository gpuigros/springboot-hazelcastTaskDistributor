package com.puigros.ws.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Global Exceltion Handler
 * @author crequena
 * @since JDK1.8
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Service exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ResponseEntity<Object> exception(Exception ex) {
        log.error("------------------ ServiceException: ", ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }




}
