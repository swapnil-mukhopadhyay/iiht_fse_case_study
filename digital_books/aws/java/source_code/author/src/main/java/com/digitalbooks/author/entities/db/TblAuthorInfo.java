package com.digitalbooks.author.entities.db;

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
public class TblAuthorInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHOR_ID")
	private Long authorId;
	
	@Column(name = "NAME", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "parentTblAuthorInfo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TblAuthorBook> tblAuthorBookList;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TblAuthorBook> getTblAuthorBookList() {
		return tblAuthorBookList;
	}

	public void setTblAuthorBookList(List<TblAuthorBook> tblAuthorBookList) {
		this.tblAuthorBookList = tblAuthorBookList;
	}
	
}
