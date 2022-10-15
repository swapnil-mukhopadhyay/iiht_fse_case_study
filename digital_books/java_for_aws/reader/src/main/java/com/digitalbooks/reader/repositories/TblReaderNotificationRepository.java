package com.digitalbooks.reader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.reader.entities.db.TblReaderNotification;

@Repository
public interface TblReaderNotificationRepository extends JpaRepository<TblReaderNotification, Long> {

}
