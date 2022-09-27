package com.digitalbooks.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.book.entities.db.TblBookContent;

public interface TblBookContentRepository extends JpaRepository<TblBookContent, Long>{
	
	

}
