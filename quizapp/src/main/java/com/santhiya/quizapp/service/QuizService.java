package com.santhiya.quizapp.service;

import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.model.QuestionWrapper;
import com.santhiya.quizapp.model.Quiz;
import com.santhiya.quizapp.model.Response;
import com.santhiya.quizapp.repository.QuestionRepository;
import com.santhiya.quizapp.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuestionRepository questionRepository;
    QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<String> createQuiz(String category, int questionsCount, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, questionsCount);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("Quiz created successfully with ID: " + quiz.getId(), HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {

        Optional<Quiz> quiz=quizRepository.findById(id);
        List<Question>questions=quiz.get().getQuestions();
        List<QuestionWrapper> questionWrappers=new ArrayList<>();
        for(Question question:questions){
            QuestionWrapper questionWrapper=new QuestionWrapper(question.getId(),question.getQuestion(),question.getA(),question.getB(),question.getC(),question.getD());
            questionWrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);

    }

    public ResponseEntity<Integer> evaluate(int id, List<Response> response) {
        Quiz quiz=quizRepository.findById(id).get();
        List<Question> questions=quiz.getQuestions();
        int right=0;
       int i=0;
       for(Response response1:response){
           if(response1.getResponse().equals(questions.get(i).getAnswer())){
               right++;
           }
           i++;
       }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

