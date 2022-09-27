package com.digitalbooks.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.book.entities.db.TblBookInfo;

public interface TblBookInfoRepository extends JpaRepository<TblBookInfo, Long>{
	
	

}
