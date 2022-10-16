package com.digitalbooks.author.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitalbooks.author.entities.payload.BookPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;

@FeignClient(url = "${digitalbooks.book.url}",value = "BOOK")
public interface BookClient {
	
	@PostMapping("/create")
	public BookPayload createBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException;
	
	@PutMapping("/edit")
	public BookPayload editBook(@RequestBody BookPayload bookPayload) throws DigitalBooksException;
	
	@GetMapping("/{authorId}/all")
	public BookPayload getAllBooksForAuthor(@PathVariable Long authorId) throws DigitalBooksException;

}
