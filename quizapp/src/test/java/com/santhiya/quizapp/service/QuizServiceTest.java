package com.santhiya.quizapp.service;

import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.model.QuestionWrapper;
import com.santhiya.quizapp.model.Quiz;
import com.santhiya.quizapp.model.Response;
import com.santhiya.quizapp.repository.QuestionRepository;
import com.santhiya.quizapp.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Question question;
    private Question question2;
    private Quiz quiz;
    private Quiz quiz2;

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


        question2 = new Question();
        question2.setId(2);
        question2.setQuestion("What is the chemical symbol for water?");
        question2.setA("H2O");
        question2.setB("CO2");
        question2.setC("O2");
        question2.setD("NaCl");
        question2.setDifficulty("Easy");
        question2.setCategory("Science");
        question2.setAnswer("H2O");


        quiz = new Quiz();
        quiz.setId(1);quiz.setTitle("Science Quiz");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        quiz.setQuestions(questions);

        quiz2 = new Quiz();
        quiz2.setId(2);
        quiz2.setTitle("General Knowledge Quiz");
        List<Question> questions2 = new ArrayList<>();
        questions2.add(question2);
        quiz2.setQuestions(questions2);
    }

    @Test
    void createQuiz() {

        List<Question> questionList = new ArrayList<>();
        questionList.add(question);
        when(questionRepository.findRandomQuestionsByCategory("Science", 1))
            .thenReturn(questionList);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);


        ResponseEntity<String> response = quizService.createQuiz("Science", 1, "Science Quiz");


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Quiz created successfully"));

        verify(questionRepository, times(1)).findRandomQuestionsByCategory("Science", 1);
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void getQuiz() {

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));


        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuiz(1);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        QuestionWrapper wrapper = response.getBody().get(0);
        assertEquals(1, wrapper.getId());
        assertEquals("What is the largest planet in our solar system?", wrapper.getQuestion());
        assertEquals("Earth", wrapper.getA());
        assertEquals("Mars", wrapper.getB());
        assertEquals("Jupiter", wrapper.getC());
        assertEquals("Saturn", wrapper.getD());

        verify(quizRepository, times(1)).findById(1);
    }

    @Test
    void evaluateWithCorrectAnswer() {

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        List<Response> responses = new ArrayList<>();
        responses.add(new Response(1, "Jupiter"));


        ResponseEntity<Integer> response = quizService.evaluate(1, responses);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());

        verify(quizRepository, times(1)).findById(1);
    }

    @Test
    void evaluateWithIncorrectAnswer() {

        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        List<Response> responses = new ArrayList<>();
        responses.add(new Response(1, "Earth"));


        ResponseEntity<Integer> response = quizService.evaluate(1, responses);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody());

        verify(quizRepository, times(1)).findById(1);
    }
}
