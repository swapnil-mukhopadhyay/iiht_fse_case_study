package com.digitalbooks.book.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.book.entities.db.TblBookSub;

@Repository
public interface TblBookSubRepository extends JpaRepository<TblBookSub, Long>{
	
	public List<TblBookSub> findByReaderId(Long readerId);
	
}
