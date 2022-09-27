package com.digitalbooks.author.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.author.entities.payload.AuthorPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;
import com.digitalbooks.author.interfaces.AuthorIf;

@RestController
@RequestMapping("api/v1/digitalbooks/author")
public class AuthorController {

	@Autowired
	private AuthorIf authorIf;

	@PostMapping("{authorId}/books")
	public AuthorPayload createBook(@RequestBody AuthorPayload authorPayload) throws DigitalBooksException {
		return authorIf.createBook(authorPayload);
	}

	@PutMapping("{authorId}/books/{bookId}")
	public AuthorPayload editBook(@RequestBody AuthorPayload authorPayload, @PathVariable Long authorId,
			@PathVariable Long bookId) throws DigitalBooksException {
		return authorIf.editBook(authorPayload, authorId, bookId);
	}

}
