package com.digitalbooks.book.exceptions;

import java.time.LocalDateTime;

public class DigitalBooksException extends Exception{

	private static final long serialVersionUID = 2065916542403224036L;
	
	private final Long statusCode;
	private final String message;
	private final LocalDateTime time;
	
	public Long getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	public DigitalBooksException(Long statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.time = LocalDateTime.now();
	}

}
