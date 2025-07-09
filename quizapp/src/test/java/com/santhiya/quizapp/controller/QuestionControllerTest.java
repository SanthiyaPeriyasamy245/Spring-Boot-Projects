package com.santhiya.quizapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santhiya.quizapp.Dto.QuestionDto;
import com.santhiya.quizapp.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private ObjectMapper objectMapper;
    private QuestionDto questionDto;
    private List<QuestionDto> questionDtoList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
        objectMapper = new ObjectMapper();

        // Setup test QuestionDto
        questionDto = new QuestionDto();
        questionDto.setId(1);
        questionDto.setQuestion("What is the largest planet in our solar system?");
        questionDto.setA("Earth");
        questionDto.setB("Mars");
        questionDto.setC("Jupiter");
        questionDto.setD("Saturn");
        questionDto.setDifficulty("Medium");
        questionDto.setCategory("Science");
        questionDto.setAnswer("Jupiter");


        questionDtoList = new ArrayList<>();
        questionDtoList.add(questionDto);

    }

    @Test
    void getQuestions_ShouldReturnAllQuestions() throws Exception {

        when(questionService.getAllQuestions()).thenReturn(questionDtoList);


        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is("What is the largest planet in our solar system?")))
                .andExpect(jsonPath("$[0].category", is("Science")));

        verify(questionService, times(1)).getAllQuestions();
    }

    @Test
    void getQuestionsByCategory_ShouldReturnQuestionsByCategory() throws Exception {

        when(questionService.getQuestionsByCategory("Science")).thenReturn(questionDtoList);


        mockMvc.perform(get("/questions/category/Science"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].category", is("Science")));

        verify(questionService, times(1)).getQuestionsByCategory("Science");
    }

    @Test
    void addQuestion_ShouldAddQuestionAndReturnSuccess() throws Exception {

        when(questionService.addQuestion(any(QuestionDto.class))).thenReturn("Question added successfully");


        mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Question added successfully"));

        verify(questionService, times(1)).addQuestion(any(QuestionDto.class));
    }

    @Test
    void deleteQuestion_ShouldDeleteQuestionAndReturnNoContent() throws Exception {

        doNothing().when(questionService).deleteQuestion(anyInt());


        mockMvc.perform(delete("/questions/1"))
                .andExpect(status().isNoContent());

        verify(questionService, times(1)).deleteQuestion(1);
    }

    @Test
    void updateQuestion_ShouldUpdateQuestionAndReturnUpdatedQuestion() throws Exception {

        when(questionService.updateQuestion(anyInt(), any(QuestionDto.class))).thenReturn(questionDto);


        mockMvc.perform(put("/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is("What is the largest planet in our solar system?")))
                .andExpect(jsonPath("$.category", is("Science")));

        verify(questionService, times(1)).updateQuestion(eq(1), any(QuestionDto.class));
    }
}
