package com.digitalbooks.author.entities.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TblAuthorBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHOR_BOOK_ID")
	private Long authorBookId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ID")
	private TblAuthorInfo parentTblAuthorInfo;
	
	@Column(name = "BOOK_ID")
	private Long bookId;

	public Long getAuthorBookId() {
		return authorBookId;
	}

	public void setAuthorBookId(Long authorBookId) {
		this.authorBookId = authorBookId;
	}

	public TblAuthorInfo getParentTblAuthorInfo() {
		return parentTblAuthorInfo;
	}

	public void setParentTblAuthorInfo(TblAuthorInfo parentTblAuthorInfo) {
		this.parentTblAuthorInfo = parentTblAuthorInfo;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
}
