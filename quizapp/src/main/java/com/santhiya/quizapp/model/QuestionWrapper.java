package com.santhiya.quizapp.model;

import lombok.Data;

@Data
public class QuestionWrapper {
    private  int  id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;

    public QuestionWrapper(int id, String question, String a, String b, String c, String d) {
        this.id = id;
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
