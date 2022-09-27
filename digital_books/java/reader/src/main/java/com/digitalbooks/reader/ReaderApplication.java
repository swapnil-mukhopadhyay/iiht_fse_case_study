package com.digitalbooks.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderApplication.class, args);
	}

}
