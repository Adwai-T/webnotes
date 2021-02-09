package com.example.Notes.controllers;

import com.example.Notes.models.Accept;
import com.example.Notes.models.ErrorMessage;
import com.example.Notes.models.ServerMessage;
import com.example.Notes.models.SuccessMessage;
import com.example.Notes.repositories.AcceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

@Validated
@RestController
@RequestMapping("steam")
public class AcceptController {

    @Autowired
    private AcceptRepository repository;

//    @Value("${STEAMID}")
//    private String steamID;

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

    @GetMapping("inventory")
    public ResponseEntity<ServerMessage> getInventory(
            @RequestParam(defaultValue = "") String steamId
    ) {
//            https://steamcommunity.com/inventory/76561198865293952/440/2?l=english&count=5000
        String url = "";
        String cookiesString = "sessionid=" + generateSessionId() + ";steamCountry=IN%7Ce744269b3c4e531facb33ecaff29eb44";

        if(steamId.equals("") || Objects.equals(steamId, null)) {
            url = "https://steamcommunity.com/inventory/76561198865293952/440/2?l=english&count=5000";
        }else {
            url = "https://steamcommunity.com/inventory/" + steamId + "/440/2?l=english&count=5000";
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept", "*/*")
                .header("Cookie", cookiesString)
//                .header("Origin", "https://steamcommunity.com")
                .uri(URI.create(url))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new SuccessMessage("Success", response.body()));
            }else {
                throw new PrivateInventoryException("Private or Friends Only inventory.");
            }

        }catch (InterruptedException | IOException | PrivateInventoryException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(e.getMessage(), "Failed to get Inventory"));
        }
    }

    private class PrivateInventoryException extends Exception{
        PrivateInventoryException(String s) {
            super(s);
        }
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

    /**
     * Generates a Steam Session Id.
     * @return sessionId.
     */
    public static String generateSessionId() {
        return new BigInteger(96, new Random()).toString(16);
    }
}

