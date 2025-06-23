package com.santhiya.quizapp.service;


import com.santhiya.quizapp.Dto.QuestionDto;
import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<List<Question>> getAllQuestions() {


        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(QuestionDto question) {
        try {

            Question user_question = modelMapper.map(question, Question.class);
            // Convert QuestionDto to Question entity using ModelMapper
            Question save = questionRepository.save(user_question);

        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuestion(QuestionDto question) {
        try {
            // Convert QuestionDto to Question entity using ModelMapper
            Question questionToDelete = modelMapper.map(question, Question.class);
            questionRepository.delete(questionToDelete);

        } catch (Exception e) {
           e.printStackTrace();
            return new ResponseEntity<>("Failed to delete question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionRepository.findByCategory((category)), HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuestion(QuestionDto question) {

        try {
            Question questionToUpdate = modelMapper.map(question, Question.class);
            Question updatedQuestion = questionRepository.save(questionToUpdate);
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
