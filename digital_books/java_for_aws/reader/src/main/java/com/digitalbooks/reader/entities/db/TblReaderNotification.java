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
public class TblReaderNotification {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTIFICATION_ID")
	private Long notificationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "READER_ID")
	private TblReaderInfo parentTblReaderInfo;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name="NOTIFICATION_DATE_TIME")
	private LocalDateTime notificationDateTime;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public TblReaderInfo getParentTblReaderInfo() {
		return parentTblReaderInfo;
	}

	public void setParentTblReaderInfo(TblReaderInfo parentTblReaderInfo) {
		this.parentTblReaderInfo = parentTblReaderInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getNotificationDateTime() {
		return notificationDateTime;
	}

	public void setNotificationDateTime(LocalDateTime notificationDateTime) {
		this.notificationDateTime = notificationDateTime;
	}
	
	
}
