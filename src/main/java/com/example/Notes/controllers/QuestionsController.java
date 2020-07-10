package com.example.Notes.controllers;

import com.example.Notes.models.Questions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class QuestionsController{

    @Value("${WELCOME}")
    private String welcomeMessage;

    @GetMapping("")
    public String welcome(){
        return welcomeMessage;
    }

    @GetMapping("questions")
    public Questions questionsGet(){
        return new Questions("adwait", "question1", "This is a Question", 1);
    }

    @PostMapping("questions")
    public Questions questionsPost(@RequestBody Questions questions){
        System.out.println(questions);
        return questions;
    }

}
