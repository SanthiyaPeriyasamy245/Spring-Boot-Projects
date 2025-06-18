package com.santhiya.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
