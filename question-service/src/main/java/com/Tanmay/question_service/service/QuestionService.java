package com.Tanmay.question_service.service;

import com.Tanmay.question_service.dao.QuestionDao;
import com.Tanmay.question_service.model.Question;
import com.Tanmay.question_service.model.QuestionWrapper;
import com.Tanmay.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    // Fetch all questions
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    // Fetch questions by category
    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    // Add a new question
    public String addQuestion(Question question) {
        questionDao.save(question);
        return "success";
    }

    // Get random question IDs for quiz
    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, int numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    // Get full question details from list of IDs
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Integer id : questionIds) {
            Question question = questionDao.findById(id).orElse(null);
            if (question != null) {
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setId(question.getId());
                wrapper.setQuestionTitle(question.getQuestionTitle());
                wrapper.setOption1(question.getOption1());
                wrapper.setOption2(question.getOption2());
                wrapper.setOption3(question.getOption3());
                wrapper.setOption4(question.getOption4());
                wrappers.add(wrapper);
            }
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    // Calculate score based on user responses
    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;

        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).orElse(null);
            if (question != null && response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
