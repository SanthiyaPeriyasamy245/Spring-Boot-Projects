package com.santhiya.quizapp.controller;

import com.santhiya.quizapp.Dto.QuestionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.santhiya.quizapp.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestions()
    {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable String category)
    {
        return  ResponseEntity.ok(questionService.getQuestionsByCategory(category));
    }

    @PostMapping
    public ResponseEntity<String>addQuestion(@Valid @RequestBody QuestionDto question)
    {
     return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addQuestion(question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id)
    {
          questionService.deleteQuestion(id);
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable int id, @Valid @RequestBody QuestionDto question)
    {
         return  ResponseEntity.ok(questionService.updateQuestion(id,question));
    }

}
