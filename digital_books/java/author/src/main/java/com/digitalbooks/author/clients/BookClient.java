package com.digitalbooks.author.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitalbooks.author.entities.payload.BookPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;

@FeignClient("BOOK")
public interface BookClient {

	public static final String BOOK_URL = "api/v1/digitalbooks/books";
	
	@PostMapping(BOOK_URL+"/create")
	public BookPayload createBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException;
	
	@PutMapping(BOOK_URL+"/edit")
	public BookPayload editBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException;

}
