package com.Tanmay.quizapp.dao;

import com.Tanmay.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {

}
