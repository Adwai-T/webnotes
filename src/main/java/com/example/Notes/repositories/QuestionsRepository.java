package com.example.Notes.repositories;

import com.example.Notes.models.Questions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionsRepository extends MongoRepository<Questions, String> {
}
