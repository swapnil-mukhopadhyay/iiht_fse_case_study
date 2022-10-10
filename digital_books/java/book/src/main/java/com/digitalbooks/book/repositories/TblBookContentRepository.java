package com.digitalbooks.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.book.entities.db.TblBookContent;

@Repository
public interface TblBookContentRepository extends JpaRepository<TblBookContent, Long>{
	
	
}
