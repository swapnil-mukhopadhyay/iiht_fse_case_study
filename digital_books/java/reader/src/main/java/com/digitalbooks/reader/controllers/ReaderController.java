package com.digitalbooks.reader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.reader.entities.payload.BookPurchasePayload;
import com.digitalbooks.reader.entities.payload.PaymentInvoicePayload;
import com.digitalbooks.reader.entities.payload.ReaderPayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;
import com.digitalbooks.reader.interfaces.ReaderIf;

@RestController
@RequestMapping("api/v1/digitalbooks/readers")
public class ReaderController {
	
	@Autowired
	private ReaderIf readerIf;
	
	@PostMapping("books/buy")
	public PaymentInvoicePayload buyBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		return readerIf.buyBook(bookPurchasePayload);
	}
	
	@GetMapping("{emailId}/books")
	public ReaderPayload getSubscribedBooks(@PathVariable String emailId) throws DigitalBooksException {
		return readerIf.getSubscribedBooks(emailId);
	}
	
	@GetMapping("{emailId}/books/{bookId}")
	public ReaderPayload readBook(@PathVariable String emailId, @PathVariable Long bookId) throws DigitalBooksException {
		return readerIf.readBook(emailId, bookId);
	}
	
	@PostMapping("{emailId}/books/find")
	public ReaderPayload findBookByPaymentId(@PathVariable String emailId, @RequestBody Long paymentId)
			throws DigitalBooksException {
		return readerIf.findBookByPaymentId(emailId,paymentId);
	}
	
	@PostMapping("{emailId}/books/{bookId}/refund")
	public ReaderPayload refundBook(@RequestBody BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		return readerIf.refundBook(bookPurchasePayload);
	}
	
	@GetMapping("{emailId}/notification")
	public ReaderPayload getNotifications(@PathVariable String emailId) {
		return readerIf.getNotifications(emailId);
	}

}
