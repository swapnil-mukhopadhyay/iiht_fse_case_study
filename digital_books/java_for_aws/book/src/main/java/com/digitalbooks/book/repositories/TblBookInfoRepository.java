package com.digitalbooks.book.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.book.entities.db.TblBookInfo;

@Repository
public interface TblBookInfoRepository extends JpaRepository<TblBookInfo, Long>{
	
	public List<TblBookInfo> findByAuthorId(Long authorId);

}
