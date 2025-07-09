package com.santhiya.quizapp.repository;

import com.santhiya.quizapp.model.Question;
import com.santhiya.quizapp.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuizRepositoryTestIT extends AbstractIntegrationTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Question scienceQuestion1;
    private Question scienceQuestion2;
    private Question geographyQuestion;

    @BeforeEach
    void setUp() {

        quizRepository.deleteAll();
        questionRepository.deleteAll();


        scienceQuestion1 = new Question();
        scienceQuestion1.setQuestion("What is the largest planet in our solar system?");
        scienceQuestion1.setA("Earth");
        scienceQuestion1.setB("Mars");
        scienceQuestion1.setC("Jupiter");
        scienceQuestion1.setD("Saturn");
        scienceQuestion1.setDifficulty("Medium");
        scienceQuestion1.setCategory("Science");
        scienceQuestion1.setAnswer("Jupiter");

        scienceQuestion2 = new Question();
        scienceQuestion2.setQuestion("What is the smallest planet in our system?");
        scienceQuestion2.setA("Earth");
        scienceQuestion2.setB("Mercury");
        scienceQuestion2.setC("Jupiter");
        scienceQuestion2.setD("Saturn");
        scienceQuestion2.setDifficulty("Medium");
        scienceQuestion2.setCategory("Science");
        scienceQuestion2.setAnswer("Mercury");

        geographyQuestion = new Question();
        geographyQuestion.setQuestion("What is the capital of France?");
        geographyQuestion.setA("Berlin");
        geographyQuestion.setB("Madrid");
        geographyQuestion.setC("Paris");
        geographyQuestion.setD("Rome");
        geographyQuestion.setDifficulty("Easy");
        geographyQuestion.setCategory("Geography");
        geographyQuestion.setAnswer("Paris");

        scienceQuestion1 = questionRepository.save(scienceQuestion1);
        scienceQuestion2 = questionRepository.save(scienceQuestion2);
        geographyQuestion = questionRepository.save(geographyQuestion);
    }

    @Test
    void createQuizTest() {

        Quiz quiz = new Quiz();
        quiz.setTitle("Science Quiz");


        List<Question> questionList = new ArrayList<>();
        questionList.add(scienceQuestion1);
        questionList.add(scienceQuestion2);
        quiz.setQuestions(questionList);


        Quiz savedQuiz = quizRepository.save(quiz);


        assertNotNull(savedQuiz.getId(), "Quiz ID should not be null");
        assertEquals("Science Quiz", savedQuiz.getTitle(), "Quiz title should match");
        assertEquals(2, savedQuiz.getQuestions().size(), "Quiz should have 2 questions");
    }

    @Test
    void findAllQuizzes() {

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        List<Question> questions = new ArrayList<>();
        questions.add(scienceQuestion1);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);


        List<Quiz> quizzes = quizRepository.findAll();


        assertFalse(quizzes.isEmpty(), "Quiz list should not be empty");
        assertTrue(quizzes.size() >= 1, "Should have at least one quiz");
    }

    @Test
    @Transactional
    void getQuizById() {

        Quiz quiz = new Quiz();
        quiz.setTitle("Geography Quiz");
        List<Question> questions = new ArrayList<>();
        questions.add(geographyQuestion);
        quiz.setQuestions(questions);
        Quiz savedQuiz = quizRepository.save(quiz);


        Optional<Quiz> retrievedQuiz = quizRepository.findById(savedQuiz.getId());


        assertTrue(retrievedQuiz.isPresent(), "Quiz should be found by ID");
        assertEquals("Geography Quiz", retrievedQuiz.get().getTitle(), "Quiz title should match");
        assertNotNull(retrievedQuiz.get().getQuestions(), "Questions list should not be null");
        assertEquals(1, retrievedQuiz.get().getQuestions().size(), "Quiz should have 1 question");
    }

    @Test
    @Transactional
    void evaluateQuiz() {

        Quiz quiz = new Quiz();
        quiz.setTitle("Science Quiz");
        List<Question> questions = new ArrayList<>();
        questions.add(scienceQuestion1);
        questions.add(scienceQuestion2);
        quiz.setQuestions(questions);
        Quiz savedQuiz = quizRepository.save(quiz);


        Quiz retrievedQuiz = quizRepository.findById(savedQuiz.getId()).orElseThrow();
        List<Question> retrievedQuestions = retrievedQuiz.getQuestions();


        int correctAnswers = 0;
        for (Question question : retrievedQuestions) {
            if (question.getAnswer().equals("Jupiter") || question.getAnswer().equals("Mercury")) {
                correctAnswers++;
            }
        }


        assertEquals(2, correctAnswers, "Should have 2 correct answers");
        assertEquals(2, retrievedQuestions.size(), "Should have 2 questions");
    }

    @Test
    void deleteQuiz() {

        Quiz quiz = new Quiz();
        quiz.setTitle("Quiz to Delete");
        quiz.setQuestions(new ArrayList<>());
        Quiz savedQuiz = quizRepository.save(quiz);


        quizRepository.deleteById(savedQuiz.getId());


        Optional<Quiz> deletedQuiz = quizRepository.findById(savedQuiz.getId());


        assertFalse(deletedQuiz.isPresent(), "Quiz should be deleted");
    }
}
