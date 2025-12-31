package com.Tanmay.quiz_service.service;

import com.Tanmay.quiz_service.dao.QuizDao;
import com.Tanmay.quiz_service.feign.QuizInterface;
import com.Tanmay.quiz_service.model.QuestionWrapper;
import com.Tanmay.quiz_service.model.Quiz;
import com.Tanmay.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        ResponseEntity<List<Integer>> questionsResponse = quizInterface.getQuestionsForQuiz(category, numQ);
        List<Integer> questionIds = questionsResponse.getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizDao.save(quiz);

        return ResponseEntity.ok("Quiz created successfully with id: " + quiz.getId());
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer quizId) {
        Optional<Quiz> quizOpt = quizDao.findById(quizId);
        if (quizOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Integer> questionIds = quizOpt.get().getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionsResponse = quizInterface.getQuestionsFromId(questionIds);

        return ResponseEntity.ok(questionsResponse.getBody());
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        Optional<Quiz> quizOpt = quizDao.findById(quizId);
        if (quizOpt.isEmpty()) return ResponseEntity.notFound().build();

        ResponseEntity<Integer> scoreResponse = quizInterface.getScore(responses);
        return ResponseEntity.ok(scoreResponse.getBody());
    }
}
