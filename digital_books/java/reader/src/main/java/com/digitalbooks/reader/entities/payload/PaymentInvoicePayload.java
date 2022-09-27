package com.digitalbooks.reader.entities.payload;

import java.time.LocalDateTime;

import com.digitalbooks.reader.entities.dto.BookDto;

public class PaymentInvoicePayload {
	
	private Long paymentId;
	private LocalDateTime paymentDateTime;
	private BookDto bookDto;
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}
	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}
	public BookDto getBookDto() {
		return bookDto;
	}
	public void setBookDto(BookDto bookDto) {
		this.bookDto = bookDto;
	}
	

}
