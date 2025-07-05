package com.santhiya.quizapp.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionDto  {
    private  int  id;
    @NotNull(message = "Question cannot be null")
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    @NotBlank(message = "Answer cannot be empty")
    private String answer;
    private  String difficulty;
    private String category;
}
