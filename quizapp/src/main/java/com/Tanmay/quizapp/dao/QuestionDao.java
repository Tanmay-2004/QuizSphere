package com.Tanmay.quizapp.dao;

import com.Tanmay.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // It's good practice to add the @Repository annotation
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    // The fix is changing random() to RAND() for MySQL compatibility
    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}