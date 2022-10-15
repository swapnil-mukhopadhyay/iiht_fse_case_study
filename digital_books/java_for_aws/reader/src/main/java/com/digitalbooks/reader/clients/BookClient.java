package com.digitalbooks.reader.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitalbooks.reader.entities.payload.BookPayload;
import com.digitalbooks.reader.entities.payload.BookPurchasePayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;

@FeignClient(url = "${digitalbooks.book.url}",value = "BOOK")
public interface BookClient {

	@PostMapping("/buy")
	public BookPayload buyBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	@GetMapping("/subscriptions/{readerId}")
	public BookPayload getSubscribedBooks(@PathVariable Long readerId) throws DigitalBooksException;

	@PostMapping("/read")
	public BookPayload readBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;

	@PostMapping("/unsubscribe")
	public BookPayload unsubscribeBook(@RequestBody BookPurchasePayload bookPurchasePayload)
			throws DigitalBooksException;

}
