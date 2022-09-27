package com.digitalbooks.reader.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.digitalbooks.common.models.NotificationPayload;
import com.digitalbooks.reader.interfaces.ReaderIf;

@Service
public class ReaderKafkaListener {
	
	@Autowired
	private ReaderIf readerIf;

	private static final String READER_NOTIFICATION_TOPIC = "notification";

	@KafkaListener(topics = READER_NOTIFICATION_TOPIC, groupId = "group_id", containerFactory = "userKafkaListenerFactory")
	public void consumeJson(NotificationPayload notificationPayload) {
		readerIf.postNotification(notificationPayload);
	}

}
