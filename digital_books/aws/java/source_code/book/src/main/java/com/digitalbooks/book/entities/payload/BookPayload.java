package com.digitalbooks.book.entities.payload;

import java.util.List;

import com.digitalbooks.book.entities.dto.BookDto;

public class BookPayload {

	private List<BookDto> bookDtoList;

	public List<BookDto> getBookDtoList() {
		return bookDtoList;
	}

	public void setBookDtoList(List<BookDto> bookDtoList) {
		this.bookDtoList = bookDtoList;
	}

}
