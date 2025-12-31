package com.Tanmay.quiz_service.controller;

import com.Tanmay.quiz_service.model.QuestionWrapper;
import com.Tanmay.quiz_service.model.Response;
import com.Tanmay.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("http://localhost:5173")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam int numQuestions,
                                             @RequestParam String title) {
        return quizService.createQuiz(category, numQuestions, title);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestion(quizId);
    }

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId,
                                              @RequestBody List<Response> responses) {
        return quizService.calculateResult(quizId, responses);
    }
}
