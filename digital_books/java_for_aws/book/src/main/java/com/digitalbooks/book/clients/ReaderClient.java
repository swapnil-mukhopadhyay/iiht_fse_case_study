package com.digitalbooks.book.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitalbooks.book.entities.payload.NotificationPayload;

@FeignClient("READER")
public interface ReaderClient {
	
	public static final String READER_URL = "api/v1/digitalbooks/readers";
	
	@PostMapping(READER_URL+"/notification")
	public NotificationPayload postNotification(@RequestBody NotificationPayload notificationPayload);

}
