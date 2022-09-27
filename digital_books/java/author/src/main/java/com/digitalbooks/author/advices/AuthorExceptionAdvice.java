package com.digitalbooks.author.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.digitalbooks.author.entities.payload.ExceptionPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;

@ControllerAdvice
public class AuthorExceptionAdvice {

	@ExceptionHandler(DigitalBooksException.class)
	public ResponseEntity<ExceptionPayload> handleMovieException(DigitalBooksException digitalBooksException) {
		ExceptionPayload exceptionPayload = new ExceptionPayload();
		exceptionPayload.setComponent("Author");
		exceptionPayload.setMessage(digitalBooksException.getMessage());
		exceptionPayload.setStatusCode(digitalBooksException.getStatusCode());
		exceptionPayload.setTime(digitalBooksException.getTime());
		return ResponseEntity.status(HttpStatus.OK).body(exceptionPayload);
	}

}
