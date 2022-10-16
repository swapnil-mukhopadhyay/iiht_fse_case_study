package com.digitalbooks.book.entities.db;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TblBookInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID")
	private Long bookId;
	
	@Column(name = "AUTHOR_ID")
	private Long authorId;
	
	@Column(name = "LOGO")
	private String logo;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "AUTHOR")
	private String author;
	
	@Column(name = "PUBLISHER")
	private String publisher;
	
	@Column(name = "PUBLISHED_DATE")
	private LocalDate publishedDate;
	
	@Column(name = "ACTIVE")
	private Boolean active;
	
	@OneToMany(mappedBy = "parentTblBookInfo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblBookSub> tblBookSubList;
	
	@OneToOne(mappedBy = "parentTblBookInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private TblBookContent childTblBookContent;
	
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public LocalDate getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<TblBookSub> getTblBookSubList() {
		return tblBookSubList;
	}
	public void setTblBookSubList(List<TblBookSub> tblBookSubList) {
		this.tblBookSubList = tblBookSubList;
	}
	public TblBookContent getChildTblBookContent() {
		return childTblBookContent;
	}
	public void setChildTblBookContent(TblBookContent childTblBookContent) {
		this.childTblBookContent = childTblBookContent;
	}
	
	
}
