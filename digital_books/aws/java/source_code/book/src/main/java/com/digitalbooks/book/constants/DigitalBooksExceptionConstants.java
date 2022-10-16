package com.digitalbooks.book.constants;

public abstract class DigitalBooksExceptionConstants {

	public static final String BOOK_BLOCKED_OR_DOESNT_EXIST = "Book is either blocked or does not exist.";
	public static final Long STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST = 604L;
	
	public static final String INVALID_PAYLOAD = "Invalid Payload.";
	public static final Long STATUS_CODE_INVALID_PAYLOAD = 600L;
	

	private DigitalBooksExceptionConstants() {

	}
}
