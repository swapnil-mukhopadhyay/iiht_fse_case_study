package com.digitalbooks.reader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.reader.entities.db.TblReaderInfo;

@Repository
public interface TblReaderInfoRepository extends JpaRepository<TblReaderInfo, Long> {

	public Optional<TblReaderInfo> findByEmailId(String emailId);
}
