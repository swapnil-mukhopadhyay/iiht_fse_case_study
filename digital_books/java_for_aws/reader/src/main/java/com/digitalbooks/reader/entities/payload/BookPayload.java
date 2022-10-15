package com.digitalbooks.reader.entities.payload;

import java.util.List;

import com.digitalbooks.reader.entities.dto.BookDto;

public class BookPayload {
	
	private List<BookDto> bookDtoList;
	
	public List<BookDto> getBookDtoList() {
		return bookDtoList;
	}
	public void setBookDtoList(List<BookDto> bookDtoList) {
		this.bookDtoList = bookDtoList;
	}
	
}
