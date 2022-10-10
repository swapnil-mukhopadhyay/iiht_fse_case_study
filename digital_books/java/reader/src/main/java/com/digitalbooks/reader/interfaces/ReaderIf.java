package com.digitalbooks.reader.interfaces;

import com.digitalbooks.common.models.NotificationPayload;
import com.digitalbooks.reader.entities.payload.BookPurchasePayload;
import com.digitalbooks.reader.entities.payload.PaymentInvoicePayload;
import com.digitalbooks.reader.entities.payload.ReaderPayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;

public interface ReaderIf {
	
	public PaymentInvoicePayload buyBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;
	
	public ReaderPayload getSubscribedBooks(String emailId) throws DigitalBooksException;
	
	public ReaderPayload readBook(String emailId, Long bookId) throws DigitalBooksException;
	
	public PaymentInvoicePayload findBookByPaymentId(String emailId, Long paymentId) throws DigitalBooksException;
	
	public ReaderPayload refundBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException;
	
	public void postNotification(NotificationPayload notificationPayload);
	
	public ReaderPayload getNotifications(String emailId);
	
}
