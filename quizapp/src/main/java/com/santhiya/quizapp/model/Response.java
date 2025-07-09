package com.santhiya.quizapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response
{
    private int id;
    private String response;

    public Response(int id, String response) {
        this.id = id;
        this.response = response;
    }
}
