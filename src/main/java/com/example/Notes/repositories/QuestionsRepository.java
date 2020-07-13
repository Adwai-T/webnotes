package com.example.Notes.repositories;

import com.example.Notes.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionsRepository extends MongoRepository<Question, String> {
}
