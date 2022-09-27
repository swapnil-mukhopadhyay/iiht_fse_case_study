/**
 * 
 */
package com.digitalbooks.book.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.book.entities.payload.BookPayload;
import com.digitalbooks.book.entities.payload.BookPurchasePayload;
import com.digitalbooks.book.exceptions.DigitalBooksException;
import com.digitalbooks.book.interfaces.BookIf;

@RestController
@RequestMapping("api/v1/digitalbooks/books")
public class BookController {

	@Autowired
	private BookIf bookIf;

	@GetMapping("search")
	public BookPayload searchBooks(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "price", required = false) Double price,
			@RequestParam(name = "publisher", required = false) String publisher) {
		return bookIf.searchBooks(category, author, price, publisher);
	}

	@PostMapping("buy")
	public BookPayload buyBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		return bookIf.buyBook(bookPurchasePayload);
	}

	@GetMapping("subscriptions/{readerId}")
	public BookPayload getSubscribedBooks(@PathVariable Long readerId) {
		return bookIf.getSubscribedBooks(readerId);
	}

	@PostMapping("read")
	public BookPayload readBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		return bookIf.readBook(bookPurchasePayload);
	}

	@PostMapping("create")
	public BookPayload createBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException {
		return bookIf.createBook(bookPayload);
	}

	@PutMapping("edit")
	public BookPayload editBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException {
		return bookIf.editBook(bookPayload);
	}

	@PostMapping("unsubscribe")
	public BookPayload unsubscribeBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		return bookIf.unsubscribeBook(bookPurchasePayload);
	}

}
