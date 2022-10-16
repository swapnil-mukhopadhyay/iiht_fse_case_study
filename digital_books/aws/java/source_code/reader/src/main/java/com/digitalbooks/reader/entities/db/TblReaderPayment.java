package com.digitalbooks.reader.entities.db;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TblReaderPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYMENT_ID")
	private Long paymentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "READER_ID")
	private TblReaderInfo parentTblReaderInfo;

	@Column(name = "BOOK_ID")
	private Long bookId;

	@Column(name = "PAYMENT_DATE_TIME")
	private LocalDateTime paymentDateTime;

	@Column(name = "IS_REFUNDED")
	private Boolean isRefunded = false;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public TblReaderInfo getParentTblReaderInfo() {
		return parentTblReaderInfo;
	}

	public void setParentTblReaderInfo(TblReaderInfo parentTblReaderInfo) {
		this.parentTblReaderInfo = parentTblReaderInfo;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	public Boolean getIsRefunded() {
		return isRefunded;
	}

	public void setIsRefunded(Boolean isRefunded) {
		this.isRefunded = isRefunded;
	}

}
