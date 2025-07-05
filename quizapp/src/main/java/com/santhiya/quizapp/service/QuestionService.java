package com.santhiya.quizapp.service;


import com.santhiya.quizapp.Dto.QuestionDto;
import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionService {

   private final QuestionRepository questionRepository;

   private final  ModelMapper modelMapper;

    public QuestionService(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public List<QuestionDto> getAllQuestions() {

            List<Question> questions=questionRepository.findAll();
            List<QuestionDto> questionDtos = new ArrayList<>();
            for(Question question : questions) {
                QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
                questionDtos.add(questionDto);
            }
            return questionDtos;
    }


    public String addQuestion(QuestionDto question) {
        Question user_question = modelMapper.map(question, Question.class);
            // Convert QuestionDto to Question entity using ModelMapper
                Question save = questionRepository.save(user_question);
                    return "Question added successfully";
    }

    public void deleteQuestion(int id) {
        log.info("Deleting question with id: {}", id);
        questionRepository.deleteById(id);
    }

    public List<QuestionDto> getQuestionsByCategory(String category) {
        log.info("Getting questions by category: {}", category);
        List<QuestionDto>questionDtos= new ArrayList<QuestionDto>();
        List<Question> questions = questionRepository.findByCategory(category);
        for(Question question : questions) {
            QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
            questionDtos.add(questionDto);
        }
        if(questionDtos.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }
        return questionDtos;
    }

    public QuestionDto updateQuestion(int id,QuestionDto question) {
        log.info("Updating question with id: {}", id);
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
        modelMapper.map(question,existingQuestion);
        questionRepository.save(existingQuestion);
        QuestionDto questionDto = modelMapper.map(existingQuestion, QuestionDto.class);
        return questionDto;

        }
    }

