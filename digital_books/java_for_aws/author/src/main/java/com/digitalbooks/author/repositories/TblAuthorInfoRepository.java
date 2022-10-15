package com.digitalbooks.author.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.author.entities.db.TblAuthorInfo;

@Repository
public interface TblAuthorInfoRepository extends JpaRepository<TblAuthorInfo, Long> {

	public Optional<TblAuthorInfo> findByName(String name);
}
