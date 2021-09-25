package com.xantar.xantarcore.rest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MESSAGE_DATA_INTEGRITY_VIOLATION = "Data integrity violation.";

	@ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		return this.handleExceptionInternal(ex, MESSAGE_DATA_INTEGRITY_VIOLATION,
				new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
