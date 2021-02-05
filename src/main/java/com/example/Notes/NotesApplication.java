package com.example.Notes;

import com.example.Notes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		basePackageClasses = com.example.Notes.repositories.UserRepository.class)
@EnableMongoRepositories(
		basePackageClasses = {
				com.example.Notes.repositories.AcceptRepository.class,
				com.example.Notes.repositories.QuestionsRepository.class,
				com.example.Notes.repositories.CommentRepository.class})
public class NotesApplication implements CommandLineRunner {

	@Value("${message.welcome}")
	private String welcomeMessage;

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(welcomeMessage);

	}
}

