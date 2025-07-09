package com.santhiya.quizapp.repository;

import com.santhiya.quizapp.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTestIT extends AbstractIntegrationTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void saveQuestion() {

        Question question2 = new Question();
        question2.setQuestion("What is the largest planet in our solar system?");
        question2.setA("Earth");
        question2.setB("Mars");
        question2.setC("Jupiter");
        question2.setD("Saturn");
        question2.setDifficulty("Medium");
        question2.setCategory("Science");
        question2.setAnswer("Jupiter");

        Question savedQuestion = questionRepository.save(question2);
        assertNotNull(savedQuestion.getId());
    }

    @Test
    void findAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        assertFalse(questions.isEmpty());
        assertEquals(1, questions.size());
    }

    @Test
    void findByCategory() {
        List<Question> questions = questionRepository.findByCategory("Science");
        assertFalse(questions.isEmpty());
        assertEquals(1, questions.size());
    }

    @Test
    void findRandomQuestionsByCategory() {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory("Science", 1);
        assertFalse(questions.isEmpty());
        assertEquals(1, questions.size());
        assertEquals("Science", questions.get(0).getCategory());
    }

    @Test
    void findByCategoryWithNoQuestions() {
        List<Question> questions = questionRepository.findByCategory("NonExistentCategory");
        assertTrue(questions.isEmpty(), "Expected no questions for a non-existent category");
    }
}