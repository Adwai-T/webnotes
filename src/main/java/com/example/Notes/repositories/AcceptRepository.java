package com.example.Notes.repositories;

import com.example.Notes.models.Accept;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AcceptRepository extends MongoRepository<Accept, String> {

    public Optional<Accept> findByName(String name);
    public Optional<List<Accept>> findAllByName(String name);
    public Optional<List<Accept>> findAllByName(String name, PageRequest of);

    public Optional<List<Accept>> findAllByQuality(String quality, PageRequest of);
    public Optional<Accept> findByMarketName(String marketName);

}
