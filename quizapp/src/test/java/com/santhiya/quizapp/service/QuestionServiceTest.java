package com.santhiya.quizapp.service;

import com.santhiya.quizapp.Dto.QuestionDto;
import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private QuestionService questionService;

    private Question question;
    private QuestionDto questionDto;
    private List<Question> questionList;

    @BeforeEach
    void setUp() {

        question = new Question();
        question.setId(1);
        question.setQuestion("What is the largest planet in our solar system?");
        question.setA("Earth");
        question.setB("Mars");
        question.setC("Jupiter");
        question.setD("Saturn");
        question.setDifficulty("Medium");
        question.setCategory("Science");
        question.setAnswer("Jupiter");


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


        questionList = new ArrayList<>();
        questionList.add(question);
    }

    @Test
    public void testGetAllQuestions() {

        List<QuestionDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(questionDto);

        when(questionRepository.findAll()).thenReturn(questionList);
        when(modelMapper.map(any(Question.class), eq(QuestionDto.class))).thenReturn(questionDto);


        List<QuestionDto> result = questionService.getAllQuestions();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(questionDto, result.get(0));
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void testAddQuestion() {

        when(modelMapper.map(any(QuestionDto.class), eq(Question.class))).thenReturn(question);
        when(questionRepository.save(any(Question.class))).thenReturn(question);


        String result = questionService.addQuestion(questionDto);


        assertEquals("Question added successfully", result);
        verify(questionRepository, times(1)).save(any(Question.class));
        verify(modelMapper, times(1)).map(any(QuestionDto.class), eq(Question.class));
    }

    @Test
    public void testDeleteQuestion() {

        doNothing().when(questionRepository).deleteById(anyInt());


        questionService.deleteQuestion(1);

        verify(questionRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetQuestionsByCategory() {

        when(questionRepository.findByCategory("Science")).thenReturn(questionList);
        when(modelMapper.map(any(Question.class), eq(QuestionDto.class))).thenReturn(questionDto);


        List<QuestionDto> result = questionService.getQuestionsByCategory("Science");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(questionDto, result.get(0));
        verify(questionRepository, times(1)).findByCategory("Science");
    }

    @Test
    public void testGetQuestionsByCategoryNotFound() {

        when(questionRepository.findByCategory("NonExistentCategory")).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> {
            questionService.getQuestionsByCategory("NonExistentCategory");
        });
        verify(questionRepository, times(1)).findByCategory("NonExistentCategory");
    }

    @Test
    public void testUpdateQuestion() {

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        doNothing().when(modelMapper).map(any(QuestionDto.class), any(Question.class));
        when(modelMapper.map(any(Question.class), eq(QuestionDto.class))).thenReturn(questionDto);


        QuestionDto result = questionService.updateQuestion(1, questionDto);


        assertNotNull(result);
        assertEquals(questionDto, result);
        verify(questionRepository, times(1)).findById(1);
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    public void testUpdateQuestionNotFound() {

        when(questionRepository.findById(999)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> {
            questionService.updateQuestion(999, questionDto);
        });
        verify(questionRepository, times(1)).findById(999);
        verify(questionRepository, never()).save(any(Question.class));
    }
}
