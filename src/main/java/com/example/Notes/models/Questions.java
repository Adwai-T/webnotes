package com.example.Notes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Questions {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String title;
    private String question;
    private int answer;
}
