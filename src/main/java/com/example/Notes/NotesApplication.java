package com.example.Notes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApplication implements CommandLineRunner {

	@Value("${message.welcome}")
	private String welcomeMessage;

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(welcomeMessage);

	}
}

