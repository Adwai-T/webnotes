package com.example.Notes.controllers;

import com.example.Notes.models.ErrorMessage;
import com.example.Notes.models.Question;
import com.example.Notes.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
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
    public List<Question> questions_Get(){
        return questionsRepository.findAll();
    }

    @PostMapping("questions")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Question> questions_Post(@RequestBody @Valid Question[] questions){

        List<Question> questionsList = new ArrayList<>();

        for(Question question : questions){

            questionsList.add(question);
        }

        questionsRepository.saveAll(questionsList);

        return questionsList;
    }

    @PutMapping("question/{id}")
    public ResponseEntity<Question> question_Patch(@RequestBody @Valid Question question, @PathVariable String id){

        questionsRepository.delete(questionsRepository.findById(id).get());

        questionsRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @DeleteMapping("questions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Question> questions_Delete(@RequestParam String[] id){

        List<Question> questions = new ArrayList<>();

        for(String questionID : id){
            Question question = questionsRepository.findById(questionID).get();
            questionsRepository.delete(question);
            questions.add(question);
        }

        return questions;
    }

    //Exception Handling
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("ConstraintViolationException", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    
}
