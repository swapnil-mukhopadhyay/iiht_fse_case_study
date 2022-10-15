package com.digitalbooks.reader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.reader.entities.db.TblReaderPayment;

@Repository
public interface TblReaderPaymentRepository extends JpaRepository<TblReaderPayment, Long> {
	
	public List<TblReaderPayment> findByBookId(Long bookId);

}
