package com.example.Notes.repositories;

import com.example.Notes.models.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends MongoRepository<Question, String> {

    public Optional<List<Question>> findAllByTopic(String topic, PageRequest of);
}
