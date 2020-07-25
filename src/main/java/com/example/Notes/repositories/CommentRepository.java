package com.example.Notes.repositories;

import com.example.Notes.models.UserComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<UserComment, String> {

    public Optional<UserComment> findByUserName(String userName);

    public Optional<List<UserComment>> findAllByUserName(String userName, PageRequest of);

}
