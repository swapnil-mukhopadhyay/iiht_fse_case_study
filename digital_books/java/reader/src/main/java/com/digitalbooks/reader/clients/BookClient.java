package com.digitalbooks.reader.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitalbooks.reader.entities.payload.BookPayload;
import com.digitalbooks.reader.entities.payload.BookPurchasePayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;

@FeignClient("BOOK")
public interface BookClient {

	public static final String BOOK_URL = "api/v1/digitalbooks/books";

	@PostMapping(BOOK_URL + "/buy")
	public BookPayload buyBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	@GetMapping(BOOK_URL + "/subscriptions/{readerId}")
	public BookPayload getSubscribedBooks(@PathVariable Long readerId) throws DigitalBooksException;

	@PostMapping(BOOK_URL + "/read")
	public BookPayload readBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	@PostMapping(BOOK_URL + "/unsubscribe")
	public BookPayload unsubscribeBook(@RequestBody BookPurchasePayload bookPurchasePayload)
			throws DigitalBooksException;

}
