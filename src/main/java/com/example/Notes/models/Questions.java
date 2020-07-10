package com.example.Notes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Questions {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String title;
    private String question;
    private int answer;

    @JsonCreator
    public Questions(String title, String question, int answer){
        this.title = title;
        this.question = question;
        this.answer = answer;
    }
}
