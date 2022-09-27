package com.digitalbooks.reader.advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.digitalbooks.reader.entities.payload.ExceptionPayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;

@ControllerAdvice
public class ReaderExceptionAdvice {

	@ExceptionHandler(DigitalBooksException.class)
	public ExceptionPayload handleMovieException(DigitalBooksException digitalBooksException) {
		ExceptionPayload exceptionPayload=new ExceptionPayload();
		exceptionPayload.setComponent("Reader");
		exceptionPayload.setMessage(digitalBooksException.getMessage());
		exceptionPayload.setStatusCode(digitalBooksException.getStatusCode());
		exceptionPayload.setTime(digitalBooksException.getTime());
		return exceptionPayload;
	}

}
