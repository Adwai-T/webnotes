package com.example.Notes.controllers;

import com.example.Notes.models.Questions;
import com.example.Notes.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class QuestionsController{

    @Value("${WELCOME}")
    private String welcomeMessage;

    private QuestionsRepository questionsRepository;

    @Autowired
    public QuestionsController(QuestionsRepository questionsRepository){
        this.questionsRepository = questionsRepository;
    }

    @GetMapping("")
    public String welcome(){
        return welcomeMessage;
    }

    @GetMapping("questions")
    public List<Questions> questionsGet(){
        return questionsRepository.findAll();
    }

    @PostMapping("questions")
    public Questions questionsPost(@RequestBody Questions questions){
        System.out.println(questions);
        questionsRepository.save(questions);
        return questions;
    }

}
