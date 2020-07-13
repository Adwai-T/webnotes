package com.example.Notes.controllers;

import com.example.Notes.models.KnownUsers;
import com.example.Notes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("users")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<KnownUsers> users_get(){
        return repository.findAll();
    }

    @PostMapping("user")
    public KnownUsers user_post(@RequestBody @Valid KnownUsers user){

        repository.save(user);

        return user;

    }


}
