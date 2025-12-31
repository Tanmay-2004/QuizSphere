import React from 'react';
import { useLocation, Link } from 'react-router-dom';

function Results() {
  const location = useLocation();
  const { score, totalQuestions } = location.state || { score: 0, totalQuestions: 0 };

  // Prevent division by zero if totalQuestions is 0
  const percentage = totalQuestions > 0 ? (score / totalQuestions) * 100 : 0;

  // Function to get a feedback message based on the score
  const getFeedbackMessage = () => {
    if (percentage >= 80) {
      return "Excellent Work! You're a QuizMaster!";
    } else if (percentage >= 50) {
      return "Good Job! You've got a solid score.";
    } else {
      return "Keep Practicing! You can do better.";
    }
  };

  return (
    <div className="results-container">
      <h2>Quiz Completed!</h2>
      <p className="feedback-message">{getFeedbackMessage()}</p>
      
      <div className="score-summary">
        <p>Your Score:</p>
        <div className="score-display">
          <span className="final-score">{score}</span>
          <span className="total-questions">/ {totalQuestions}</span>
        </div>
        <p className="percentage-display">{percentage.toFixed(1)}%</p>
      </div>

      <Link to="/">
        <button>Play Again</button>
      </Link>
    </div>
  );
}

export default Results;