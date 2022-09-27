package com.digitalbooks.book.entities.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class TblBookContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_CONTENT_ID")
	private Long bookContentId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_ID")
	private TblBookInfo parentTblBookInfo;

	@Lob
	@Column(name = "CONTENT")
	private String content;

	public Long getBookContentId() {
		return bookContentId;
	}
	public void setBookContentId(Long bookContentId) {
		this.bookContentId = bookContentId;
	}
	public TblBookInfo getParentTblBookInfo() {
		return parentTblBookInfo;
	}
	public void setParentTblBookInfo(TblBookInfo parentTblBookInfo) {
		this.parentTblBookInfo = parentTblBookInfo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
