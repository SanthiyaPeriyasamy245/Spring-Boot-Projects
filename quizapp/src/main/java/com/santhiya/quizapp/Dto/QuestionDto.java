package com.santhiya.quizapp.Dto;

import lombok.Data;

@Data
public class QuestionDto {
    private  int  id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private  String difficulty;
    private String category;
}
