package com.digitalbooks.book.interfaces;

import com.digitalbooks.book.entities.payload.BookPayload;
import com.digitalbooks.book.entities.payload.BookPurchasePayload;
import com.digitalbooks.book.exceptions.DigitalBooksException;

public interface BookIf {

	public BookPayload searchBooks(String category, String author, Double price, String publisher);

	public BookPayload buyBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;
	
	public BookPayload getSubscribedBooks(Long readerId);

	public BookPayload readBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	public BookPayload createBook(BookPayload bookPayload) throws DigitalBooksException;

	public BookPayload editBook(BookPayload bookPayload) throws DigitalBooksException;

	public BookPayload unsubscribeBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	public BookPayload getAllBooksForAuthor(Long authorId);

}
