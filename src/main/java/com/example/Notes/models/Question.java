package com.example.Notes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String question;

    @Min(1)
    @Max(4)
    @NotNull
    private int answer;

    @NotBlank
    private String topic;

    @NotEmpty
    private String[] answers;

    @JsonCreator
    public Question(String title, String question, int answer, String topic, String[] answers){
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.topic = topic;
        this.answers = answers;
    }
}
