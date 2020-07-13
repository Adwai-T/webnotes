package com.example.Notes.repositories;

import com.example.Notes.models.KnownUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<KnownUsers, Long> {

    public Optional<KnownUsers> findByUserName(String userName);

}
