package com.example.Notes.controllers;

import com.example.Notes.models.Accept;
import com.example.Notes.models.ErrorMessage;
import com.example.Notes.repositories.AcceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Validated
@RestController
@RequestMapping("steam")
public class AcceptController {

    @Autowired
    private AcceptRepository repository;

    public AcceptController(AcceptRepository repository) {
        this.repository = repository;
    }

    @GetMapping("accept")
    public ResponseEntity<List<Accept>> accept_Get(
                @RequestParam(defaultValue = "0") int pageNumber,
                @RequestParam(defaultValue = "20") int numberOfItems,
                @RequestHeader(value = "Authorization", defaultValue = "false")String bearerToken){

        List<Accept> accepts = repository.findAll();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(accepts);
    }

    @PutMapping("accept/update")
    public ResponseEntity<Accept> updateTrades(
            @RequestBody @Valid Accept item
    ){
        List<Accept> repoItemList = repository
                .findAllByName(
                        item.getName())
                .get();

        for(Accept accept: repoItemList) {
            if(accept.checkIsEqual(item)) {
                accept.updateWithItem(item);
                repository.save(accept);

                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(accept);
            }
        }
        repository.save(item);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(item);
    }

    @GetMapping("accept/{quality}")
    public ResponseEntity<List<Accept>> accept_Get_ByQuality(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int numberOfItems,
            @PathVariable String quality){

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(repository
                        .findAllByQuality(quality.toUpperCase(), PageRequest.of(pageNumber, numberOfItems))
                        .get());
    }

    //Exception Handling
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(
                new ErrorMessage("ConstraintViolationException", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("NoSuchElementException", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

