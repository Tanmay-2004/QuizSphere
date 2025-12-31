import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Quiz() {
  const { quizId } = useParams();
  const navigate = useNavigate();

  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const response = await axios.get(`http://localhost:8081/quiz/get/${quizId}`);
        if (response.data && response.data.length > 0) {
          setQuestions(response.data);
        } else {
          // Set an error if no questions are returned
          setError('No questions found for this quiz. It might be empty.');
        }
      } catch (err) {
        console.error("Error fetching questions:", err);
        setError('Failed to load quiz questions. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchQuestions();
  }, [quizId]);

  const handleAnswerSelect = (questionId, selectedOption) => {
    setSelectedAnswers({
      ...selectedAnswers,
      [questionId]: selectedOption,
    });
  };

  const handleSubmitQuiz = async () => {
    setLoading(true);
    const formattedResponses = Object.keys(selectedAnswers).map(questionId => ({
      id: parseInt(questionId),
      response: selectedAnswers[questionId]
    }));

    try {
        const response = await axios.post(`http://localhost:8081/quiz/submit/${quizId}`, formattedResponses);
        const score = response.data;
        navigate('/results', { state: { score: score, totalQuestions: questions.length } });
    } catch (err) {
        console.error("Error submitting quiz:", err);
        setError('There was an error submitting your quiz.');
        setLoading(false);
    }
  };

  // --- RENDER LOGIC ---

  if (loading) {
    return <div>Loading Quiz...</div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  // *** THE FIX IS HERE ***
  // Add a final check to ensure we have questions before trying to render them.
  if (questions.length === 0) {
    return <div className="error-message">This quiz has no questions.</div>;
  }

  const currentQuestion = questions[currentQuestionIndex];
  const isLastQuestion = currentQuestionIndex === questions.length - 1;

  return (
    <div className="quiz-container">
      <div className="question-header">
        <h3>Question {currentQuestionIndex + 1} of {questions.length}</h3>
        <p className="question-title">{currentQuestion.questionTitle}</p>
      </div>

      <div className="options-container">
        {[currentQuestion.option1, currentQuestion.option2, currentQuestion.option3, currentQuestion.option4].map((option, index) => (
          <div
            key={index}
            className={`option ${selectedAnswers[currentQuestion.id] === option ? 'selected' : ''}`}
            onClick={() => handleAnswerSelect(currentQuestion.id, option)}
          >
            {option}
          </div>
        ))}
      </div>

      <div className="quiz-navigation">
        {isLastQuestion ? (
          <button onClick={handleSubmitQuiz} disabled={!selectedAnswers[currentQuestion.id]}>
            Submit Quiz
          </button>
        ) : (
          <button onClick={() => setCurrentQuestionIndex(currentQuestionIndex + 1)} disabled={!selectedAnswers[currentQuestion.id]}>
            Next Question
          </button>
        )}
      </div>
    </div>
  );
}

export default Quiz;