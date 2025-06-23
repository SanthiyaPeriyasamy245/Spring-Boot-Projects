package com.santhiya.quizapp;

import com.santhiya.quizapp.repository.QuestionRepository;
import com.santhiya.quizapp.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class QuestionServiceTest
{
    private QuestionService questionService;

    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp()
    {
        questionRepository = mock(QuestionRepository.class);
        questionService = new QuestionService(questionRepository);
    }
}
