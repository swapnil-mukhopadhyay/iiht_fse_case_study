package com.digitalbooks.reader.entities.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TblReaderInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "READER_ID")
	private Long readerId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;
	
	@OneToMany(mappedBy = "parentTblReaderInfo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblReaderPayment> tblReaderPaymentList;
	
	@OneToMany(mappedBy = "parentTblReaderInfo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblReaderNotification> tblReaderNotificationList;

	public Long getReaderId() {
		return readerId;
	}

	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<TblReaderPayment> getTblReaderPaymentList() {
		return tblReaderPaymentList;
	}

	public void setTblReaderPaymentList(List<TblReaderPayment> tblReaderPaymentList) {
		this.tblReaderPaymentList = tblReaderPaymentList;
	}

	public List<TblReaderNotification> getTblReaderNotificationList() {
		return tblReaderNotificationList;
	}

	public void setTblReaderNotificationList(List<TblReaderNotification> tblReaderNotificationList) {
		this.tblReaderNotificationList = tblReaderNotificationList;
	}
	

}
