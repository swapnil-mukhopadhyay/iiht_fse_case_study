package com.digitalbooks.book.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.book.entities.db.TblBookSub;

public interface TblBookSubRepository extends JpaRepository<TblBookSub, Long>{
	
	public List<TblBookSub> findByReaderId(Long readerId);
	
}
