package com.example.Notes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UserComment {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank
    private String comment;
    
    @NotBlank
    private String topic;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @NotBlank
    private String userName;

    @JsonCreator
    public UserComment(String userName, String comment, String topic){
        this.userName = userName;
        this.comment = comment;
        this.topic  = topic;
        this.createDate = new Date(System.currentTimeMillis());
    }
}
