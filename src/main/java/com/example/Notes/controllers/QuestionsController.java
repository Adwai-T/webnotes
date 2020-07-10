package com.example.Notes.controllers;

import com.example.Notes.models.Questions;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/vi")
public class QuestionsController{

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
