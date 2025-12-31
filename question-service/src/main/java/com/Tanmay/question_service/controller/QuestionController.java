package com.Tanmay.question_service.controller;

import com.Tanmay.question_service.model.Question;
import com.Tanmay.question_service.model.QuestionWrapper;
import com.Tanmay.question_service.model.Response;
import com.Tanmay.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Get all questions
    @GetMapping("allQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get questions by category
    @GetMapping("category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    // Add a new question
    @PostMapping("add")
    public String addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // Generate random question IDs for quiz
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(
            @RequestParam String category,
            @RequestParam Integer numQ) {
        return questionService.getQuestionForQuiz(category, numQ);
    }

    // Get full question details from IDs
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsFromId(questionIds);
    }

    // Calculate score based on responses
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}
