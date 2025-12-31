package com.Tanmay.quizapp.controller;

import com.Tanmay.quizapp.model.Question;
import com.Tanmay.quizapp.model.QuestionWrapper;
import com.Tanmay.quizapp.model.Response;
import com.Tanmay.quizapp.service.QuizService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("create")
    public ResponseEntity<String>createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>>getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,@RequestParam List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
