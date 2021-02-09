package com.example.Notes;

import com.example.Notes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories(
		basePackageClasses = com.example.Notes.repositories.UserRepository.class)
@EnableMongoRepositories(
		basePackageClasses = {
				com.example.Notes.repositories.AcceptRepository.class,
				com.example.Notes.repositories.QuestionsRepository.class,
				com.example.Notes.repositories.CommentRepository.class})
@EnableSwagger2
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

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API for adwait.in",
				"Api used for interacting with adwait.in Backend",
				"1.0",
				"",
				new Contact("Adwait", "www.adwait.in", ""),
				"MIT", "", Collections.emptyList());
	}
}

