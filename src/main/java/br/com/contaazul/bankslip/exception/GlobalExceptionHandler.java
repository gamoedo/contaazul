package br.com.contaazul.bankslip.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javassist.NotFoundException;
import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class, TypeMismatchException.class })
    public ErrorMessage handleBadRequest(Exception e) {
	logger.error("handleBadRequest: StatusCode: 400");
	return new ErrorMessage("400", "Bankslip not provided in the request body");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ErrorMessage handleUnprocessableEntity(UnprocessableEntityException e) {
	logger.error("handleUnprocessableEntity: StatusCode: 422");
	return new ErrorMessage("422", "Invalid bankslip provided. The possible reasons are: A field of the provided bankslip was null or with invalid values");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFound(NotFoundException e) {
	logger.error("handleNotFound: StatusCode: 404");
	return new ErrorMessage("404", "Bankslip not found with the specified id");
    }

    @Getter
    class ErrorMessage {
	String error;
	String message;

	ErrorMessage(String error, String message) {
	    this.error = error;
	    this.message = message;
	}

    }

}
