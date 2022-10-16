package com.digitalbooks.reader.entities.payload;

import com.digitalbooks.reader.entities.dto.ReaderDto;

public class BookPurchasePayload {

	private Long bookId;
	private Long paymentId;
	private ReaderDto readerDto;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public ReaderDto getReaderDto() {
		return readerDto;
	}

	public void setReaderDto(ReaderDto readerDto) {
		this.readerDto = readerDto;
	}

}
