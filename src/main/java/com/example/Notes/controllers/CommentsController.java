package com.example.Notes.controllers;

import com.example.Notes.models.ErrorMessage;
import com.example.Notes.models.ServerMessage;
import com.example.Notes.models.SuccessMessage;
import com.example.Notes.models.UserComment;
import com.example.Notes.repositories.CommentRepository;
import com.example.Notes.security_manager.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://adwaitwebnotes.imfast.io"})
@RequestMapping("comments")
public class CommentsController {

    private CommentRepository repository;
    private JwtUtil jwtUtil;

    @Autowired
    public CommentsController(CommentRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("")
    public ResponseEntity<List<UserComment>> comments_Get(
                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int numberOfComments){



        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(repository
                                .findAll(PageRequest.of(pageNumber, numberOfComments))
                                .toList());

    }

    @GetMapping("user")
    public ResponseEntity<List<UserComment>> comments_Get_byUserName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int numberOfComments,
            @RequestHeader (value = "Authorization", defaultValue = "false") String bearerToken){

        if(!bearerToken.equals("false")) {

            String jwt = bearerToken.substring(7);

            String userName = jwtUtil.extractUserName(jwt);

            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(repository
                            .findAllByUserName(userName ,PageRequest.of(pageNumber, numberOfComments))
                            .get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("user")
    public ResponseEntity<UserComment> comment_Post(
            @RequestBody @Valid UserComment userComment,
            @RequestHeader(value = "Authorization", defaultValue = "false") String bearerToken){

        if(!bearerToken.equals("false")) {

            String jwt = bearerToken.substring(7);

            String userName = jwtUtil.extractUserName(jwt);

            UserComment comment = userComment;
            comment.setUserName(userName);

            repository.save(comment);

            return ResponseEntity.status(HttpStatus.CREATED).body(comment);

        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userComment);
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<ServerMessage> comment_Delete(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", defaultValue = "false") String bearerToken){


        if(!bearerToken.equals("false")) {

            String jwt = bearerToken.substring(7);

            String userName = jwtUtil.extractUserName(jwt);

            String role = jwtUtil.extractRole(jwt)
                    .replace("[{","")
                    .replace("}]", "")
                    .replace("authority=", "");

            UserComment userComment = repository.findById(id).get();

            if(userComment.getUserName().equals(userName)
                    || role.equals("ROLE_ADMIN")
                    || role.equals("ROLE_ASSIST")){

                repository.delete(userComment);

                return ResponseEntity
                            .status(HttpStatus.ACCEPTED)
                            .body(new SuccessMessage(
                                    "Comments Deleted",
                                    "Comments were Deleted Successfully"
                            ));
            }

        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(
                        "UnAuthorized",
                        "The user is Unauthorized to delete this comment."));

    }

    //Exception Handling
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage("ConstraintViolationException", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


}
