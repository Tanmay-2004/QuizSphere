import './App.css'
// FIX: Add 'Routes' to this import line
import { Routes, Route } from 'react-router-dom' 
import Home from './components/Home'
import Quiz from './components/Quiz'
import Results from './components/Results'

function App() {
  return (
    <div className="App">
      <header className="app-header">
        QuizMaster
      </header>

      <main>
        <div className="container">
          {/* This line was causing the error because 'Routes' wasn't imported */}
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/quiz/:quizId" element={<Quiz />} />
            <Route path="/results" element={<Results />} />
          </Routes>
        </div>
      </main>
    </div>
  )
}

export default App