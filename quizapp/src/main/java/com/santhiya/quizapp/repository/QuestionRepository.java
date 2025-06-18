package com.santhiya.quizapp.repository;


import com.santhiya.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    List<Question>findByCategory(String category);

    @Query(value="select  * from question q where q.category=:category order by random() limit :questionsCount", nativeQuery = true)

    List<Question> findRandomQuestionsByCategory(String category, int questionsCount);
}