package com.santhiya.quizapp.controller;

import com.santhiya.quizapp.Dto.QuestionDto;
import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.model.QuestionWrapper;
import com.santhiya.quizapp.model.Quiz;
import com.santhiya.quizapp.model.Response;
import com.santhiya.quizapp.service.QuizService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizService quizService;

    private MockMvc mockMvc;
    private List<QuestionWrapper> questionWrappers;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();


        questions = new ArrayList<>();
        Question question = new Question();
        question.setId(1);
        question.setQuestion("What is the largest planet in our solar system?");
        question.setA("Earth");
        question.setB("Mars");
        question.setC("Jupiter");
        question.setD("Saturn");
        question.setDifficulty("Medium");
        question.setCategory("Science");
        question.setAnswer("Jupiter");
        questions.add(question);


        questionWrappers = new ArrayList<>();
        QuestionWrapper wrapper = new QuestionWrapper(
            1,
            "What is the largest planet in our solar system?",
            "Earth",
            "jupiter",
            "saturn",
            "mars"
        );
        questionWrappers.add(wrapper);
    }

    @Test
    public void getQuizById() throws Exception {

        ResponseEntity<List<QuestionWrapper>> responseEntity = new ResponseEntity<>(questionWrappers, HttpStatus.OK);
        when(quizService.getQuiz(1)).thenReturn(responseEntity);


        mockMvc.perform(get("/quiz/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is("What is the largest planet in our solar system?")))
                .andExpect(jsonPath("$[0].a", is("Earth")))
                .andExpect(jsonPath("$[0].b", is("jupiter")))
                .andExpect(jsonPath("$[0].c", is("saturn")))
                .andExpect(jsonPath("$[0].d", is("mars")));


        verify(quizService, times(1)).getQuiz(1);
    }
    @Test
    void shouldCreateQuizByCategory() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setTitle("Science-Quiz");
        quiz.setQuestions(questions);

        ResponseEntity<String> responseEntity =
                new ResponseEntity<>("Quiz created successfully with ID: " + quiz.getId(), HttpStatus.CREATED);

        when(quizService.createQuiz("Science", 1, "Science-Quiz")).thenReturn(responseEntity);

        mockMvc.perform(post("/quiz")
                        .param("category", "Science")
                        .param("questionsCount", "1")
                        .param("title", "Science-Quiz"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Quiz created successfully with ID: 1")));

        verify(quizService, times(1)).createQuiz("Science", 1, "Science-Quiz");
    }

    @Test
    void submit() throws Exception {

        Response response=new Response(1,"Jupiter");
        List<Response> responses=new ArrayList<>();
        responses.add(response);

        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(1, HttpStatus.OK);
        when(quizService.evaluate(1,responses)).thenReturn(responseEntity);

        mockMvc.perform(post("/quiz/submit/1")
                .contentType("application/json")
                .content("[{\"id\":1,\"response\":\"Jupiter\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));

        verify(quizService,times(1)).evaluate(1, responses);
    }
}
