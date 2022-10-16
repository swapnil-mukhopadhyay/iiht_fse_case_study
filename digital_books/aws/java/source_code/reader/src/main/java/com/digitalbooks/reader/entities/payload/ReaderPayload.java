package com.digitalbooks.reader.entities.payload;

import java.util.List;

import com.digitalbooks.reader.entities.dto.BookDto;
import com.digitalbooks.reader.entities.dto.ReaderDto;

public class ReaderPayload {
	
	private ReaderDto readerDto;
	private List<BookDto> bookDtoList;
	private List<String> notifications;
	
	public ReaderDto getReaderDto() {
		return readerDto;
	}
	public void setReaderDto(ReaderDto readerDto) {
		this.readerDto = readerDto;
	}
	public List<BookDto> getBookDtoList() {
		return bookDtoList;
	}
	public void setBookDtoList(List<BookDto> bookDtoList) {
		this.bookDtoList = bookDtoList;
	}
	public List<String> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<String> notifications) {
		this.notifications = notifications;
	}
	

}
