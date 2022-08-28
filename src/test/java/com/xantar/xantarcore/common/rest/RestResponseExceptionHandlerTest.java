package com.xantar.xantarcore.common.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class RestResponseExceptionHandlerTest {


	private final RestResponseExceptionHandler handler = new RestResponseExceptionHandler();


	/*
	 * handleConflict(RuntimeException, WebRequest)
	 * */
	@Test
	void handleConflict_shouldReturn_conflictCode() {
		final ResponseEntity<Object> handled = this.handler.handleConflict(new RuntimeException(""), Mockito.mock(WebRequest.class));
		assertEquals(HttpStatus.CONFLICT, handled.getStatusCode());
	}

	/*
	 * handleDataAccessError(RuntimeException, WebRequest)
	 * */
	@Test
	void handleDataAccessError_shouldReturn_notFoundCode() {
		final ResponseEntity<Object> handled = this.handler.handleDataAccessError(new RuntimeException(""), Mockito.mock(WebRequest.class));
		assertEquals(HttpStatus.NOT_FOUND, handled.getStatusCode());
	}

	/*
	 * handleIllegalArgumentError(RuntimeException, WebRequest)
	 * */
	@Test
	void handleIllegalArgumentError_shouldReturn_notFoundCode() {
		final ResponseEntity<Object> handled = this.handler.handleIllegalArgumentError(new RuntimeException(""), Mockito.mock(WebRequest.class));
		assertEquals(HttpStatus.BAD_REQUEST, handled.getStatusCode());
	}

}
