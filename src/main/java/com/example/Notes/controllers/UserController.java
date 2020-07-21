package com.example.Notes.controllers;

import com.example.Notes.models.ErrorMessage;
import com.example.Notes.models.KnownUsers;
import com.example.Notes.models.SuccessMessage;
import com.example.Notes.repositories.UserRepository;
import com.example.Notes.security_manager.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@Validated
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://adwaitwebnotes.imfast.io"})
@RequestMapping("user")
public class UserController {

    private UserRepository repository;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("createuser")
    public ResponseEntity user_post(@RequestBody @Valid KnownUsers user)
    {
        try{
            KnownUsers knownUsers = repository.findByUserName(user.getUserName()).get();

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorMessage("UserAlreadyExists",
                            "User Already Exists, Please enter a unique UserName or login with password"));

        }catch(NoSuchElementException e){

            user.setRoles("ROLE_VISITOR");
            repository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }catch (Exception e){

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMessage(
                            "ErrorCreatingUser",
                            "There was a error creating user, Please try again later")
                    );

        }

    }

    @GetMapping("userprofile")
    public ResponseEntity user_get(@RequestHeader(value = "Authorization", defaultValue = "false") String bearerToken)
    {

        if(!bearerToken.equals("false")){

            String jwt = bearerToken.substring(7);

            String userName = jwtUtil.extractUserName(jwt);

            return ResponseEntity.ok(repository.findByUserName(userName).get());

        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage("UnauthorizedUser", "User UnAuthorized or could not be found"));
    }

    @DeleteMapping("userprofile/delete")
    public ResponseEntity user_delete(@RequestHeader(value = "Authorization", defaultValue = "false") String bearerToken)
    {

        if(!bearerToken.equals("false")){

            String jwt = bearerToken.substring(7);

            String userName = jwtUtil.extractUserName(jwt);

            try{
                repository.delete(repository.findByUserName(userName).get());
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(new SuccessMessage("SuccessFully Deleted User.", "Delete SuccessFul."));

            }catch(IllegalArgumentException e){
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ErrorMessage("IllegalArgumentsException", "Error Deleting User : " + e));

            }catch(Exception e){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorMessage("IllegalArgumentsException", "Error Deleting User : " + e));
            }

        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage("UnauthorizedUser", "User UnAuthorized or could not be found"));
    }

    //Exception Handling
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("ConstraintViolationException", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
