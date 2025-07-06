package com.santhiya.quizapp.controller;

import com.santhiya.quizapp.model.QuestionWrapper;
import com.santhiya.quizapp.model.Response;
import com.santhiya.quizapp.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {


    QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;

    }

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int questionsCount,@RequestParam String title)
    {
        return quizService.createQuiz(category, questionsCount, title);

    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable int id)
    {
      return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> response)
    {
        return quizService.evaluate(id,response);
    }
}
