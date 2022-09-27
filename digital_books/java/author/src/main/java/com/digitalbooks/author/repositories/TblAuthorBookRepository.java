package com.digitalbooks.author.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.author.entities.db.TblAuthorBook;

@Repository
public interface TblAuthorBookRepository extends JpaRepository<TblAuthorBook, Long> {

	public Optional<TblAuthorBook> findByBookId(Long bookId);

}
