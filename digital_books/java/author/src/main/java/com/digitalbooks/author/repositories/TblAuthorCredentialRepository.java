package com.digitalbooks.author.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.author.entities.db.TblAuthorCredential;

@Repository
public interface TblAuthorCredentialRepository extends JpaRepository<TblAuthorCredential, Long> {

	public Optional<TblAuthorCredential> findByUsername(String username);
}
