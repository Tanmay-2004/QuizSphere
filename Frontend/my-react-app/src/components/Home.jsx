import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // Import axios

function Home() {
  const [category, setCategory] = useState('');
  const [numQuestions, setNumQuestions] = useState(5);
  const [title, setTitle] = useState('');
  const [loading, setLoading] = useState(false); // State to handle loading
  const [error, setError] = useState(''); // State to handle errors
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!category || !numQuestions || !title) {
      setError('Please fill in all fields!');
      return;
    }

    setLoading(true);
    setError('');

    try {
      // IMPORTANT: Replace '8081' with the actual port your quiz-service is running on!
      const response = await axios.post(`http://localhost:8081/quiz/create?category=${category}&numQuestions=${numQuestions}&title=${title}`);
      
      // The backend returns a string like "Quiz created successfully with id: 5"
      // We need to extract the ID from this string.
      const responseData = response.data;
      const quizId = responseData.split(': ')[1];

      if (quizId) {
        // Navigate to the quiz page with the new quiz ID
        navigate(`/quiz/${quizId}`);
      } else {
        throw new Error("Could not create quiz or parse ID.");
      }

    } catch (err) {
      console.error("Error creating quiz:", err);
      setError('Failed to create the quiz. Please check if the backend is running and try again.');
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Create Your Quiz</h2>
      <p>Select your quiz options below to start.</p>
      
      <form onSubmit={handleSubmit} className="quiz-form">
        {/* ... (input fields remain the same) ... */}
        <div className="form-group">
          <label htmlFor="title">Quiz Title:</label>
          <input type="text" id="title" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="e.g., My Awesome Quiz"/>
        </div>
        <div className="form-group">
          <label htmlFor="category">Category:</label>
          <input type="text" id="category" value={category} onChange={(e) => setCategory(e.target.value)} placeholder="e.g., Java or Python"/>
        </div>
        <div className="form-group">
          <label htmlFor="numQuestions">Number of Questions:</label>
          <input type="number" id="numQuestions" value={numQuestions} onChange={(e) => setNumQuestions(e.target.value)} min="1" max="20"/>
        </div>
        
        {/* Show a loading message or the button */}
        <button type="submit" disabled={loading}>
          {loading ? 'Creating...' : 'Start Quiz'}
        </button>

        {/* Show an error message if something goes wrong */}
        {error && <p className="error-message">{error}</p>}
      </form>
    </div>
  );
}

export default Home;