package com.santhiya.quizapp.controller;

import com.santhiya.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.santhiya.quizapp.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/quizapp")
public class QuestionController {

    @Autowired
    QuestionService questionService;


    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions()
    {
        return questionService.getAllQuestions();
    }

    // if path variable name and mapping name parameter both are same means
    // we don;'t have to explicitly mention it in the method near by @path variable
    // otherwise we have to mention it explicitly

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {

        return questionService.getQuestionsByCategory(category);

    }
     @PostMapping
     public ResponseEntity<String>addQuestion( @RequestBody Question question) {
       return  questionService.addQuestion(question);

    }
    @DeleteMapping
    public ResponseEntity<String> deleteQuestion(@RequestBody Question question) {
        return questionService.deleteQuestion(question);
    }
    @PutMapping
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question); // Assuming addQuestion can also handle updates
    }

}
