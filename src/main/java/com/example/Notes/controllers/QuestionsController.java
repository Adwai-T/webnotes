package com.example.Notes.controllers;

import com.example.Notes.models.ErrorMessage;
import com.example.Notes.models.Question;
import com.example.Notes.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
@CrossOrigin(origins = {"http://localhost:4200", "https://adwaitwebnotes.imfast.io"})
@RequestMapping("api")
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
    public List<Question> questions_Get(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return questionsRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @GetMapping("questions/by/{topic}")
    public List<Question> questions_Get_ByTopic(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @PathVariable String topic) {

        return questionsRepository.findAllByTopic(topic, PageRequest.of(page, size)).get();
    }

    @PostMapping("questions/addquestions")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Question> questions_Post(@RequestBody @Valid Question[] questions){

        List<Question> questionsList = new ArrayList<>();

        for(Question question : questions){
            questionsList.add(question);
        }

        questionsRepository.saveAll(questionsList);

        return questionsList;
    }

    @PutMapping("questions/{id}")
    public ResponseEntity<Question> question_Patch(@RequestBody @Valid Question question, @PathVariable String id){

        questionsRepository.delete(questionsRepository.findById(id).get());

        questionsRepository.save(question);

        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @DeleteMapping("questions/deletequestions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Question> questions_Delete(@RequestParam List<String> id){

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
