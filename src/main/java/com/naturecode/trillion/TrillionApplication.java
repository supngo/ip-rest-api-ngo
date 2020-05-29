package com.naturecode.trillion;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TrillionApplication {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");

	public static void main(String[] args) {
		SpringApplication.run(TrillionApplication.class, args);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		log.info("TrillionApplication started at {}", sdf.format(timestamp));
	}
}
