package com.digitalbooks.author.constants;

public abstract class DigitalBooksExceptionConstants {

	public static final String BOOK_BLOCKED_OR_DOESNT_EXIST = "Book is either blocked or does not exist.";
	public static final Long STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST = 604L;
	
	public static final String INVALID_PAYLOAD = "Invalid Payload.";
	public static final Long STATUS_CODE_INVALID_PAYLOAD = 600L;
	
	public static final String ALREADY_SUBSCRIBED = "You are already subscribed";
	public static final Long STATUS_CODE_ALREADY_SUBSCRIBED = 650L;
	
	public static final String NOT_SUBSCRIBED = "You are not subscribed.";
	public static final Long STATUS_CODE_NOT_SUBSCRIBED = 651L;
	
	public static final String ERROR_RETRIEVING_BOOK_CONTENT = "Error retrieving book content.";
	public static final Long STATUS_CODE_ERROR_RETRIEVING_BOOK_CONTENT = 651L;
	
	public static final String INVALID_PAYMENT_ID = "Payment ID is invalid.";
	public static final Long STATUS_CODE_INVALID_PAYMENT_ID = 652L;
	
	public static final String SOMETHING_WENT_WRONG = "Something went wrong.";
	public static final Long STATUS_CODE_SOMETHING_WENT_WRONG = 653L;
	
	public static final String USERNAME_TAKEN = "Username is taken.";
	public static final Long STATUS_CODE_USERNAME_TAKEN = 654L;

	public static final String INVALID_AUTHOR_ID = "Invalid author ID";
	public static final Long STATUS_CODE_INVALID_AUTHOR_ID = 655L;

	private DigitalBooksExceptionConstants() {

	}
}
