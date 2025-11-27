import React, { useEffect, useState } from "react";
import http from "../http-common";

const OpenTriviaQuestion = () => {
  const [questions, setQuestions] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [answers, setAnswers] = useState([]);

//Html decoder  
function decodeHtml(html) {
    var txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}

//Haal vragen op uit backend
useEffect(() => {
  http.get("/questions")
    .then(res => {
      setQuestions(res.data);
    })
    .catch(err => {
      console.error("Error fetching questions:", err);
    });
}, []);

  //Huidige vraag uit de vragenlijst
  const currentQuestion = questions[currentIndex];

  const handleNext = () => {

    const updatedAnswers = [
    ...answers,
    {
      questionId: currentQuestion.id,
      answer: currentQuestion.answers[selectedAnswer],
    },
  ];

    setAnswers(updatedAnswers);
    setSelectedAnswer(null);

    //Controleer of er nog een volgende vraag is
    if (currentIndex < questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    //Als er geen vragen meer over zijn, stuur een checkanswers request naar de backend  
    } else {
        http
      .post("/checkanswers", updatedAnswers)
      .then((res) => {
        alert(res.data); 

        setCurrentIndex(0);
        setSelectedAnswer(null);
        setAnswers([]);

      })
    }
  };

// Als de questions array nog leeg is
if (questions.length === 0) {
  return (
    <div className="container mt-3">
      <h3>Loading questions...</h3>
    </div>
  );
}

return (
<div className="container text-start mt-3">
  <div>
    <h2>
      Question {currentIndex + 1}: {decodeHtml(currentQuestion.question)}
    </h2>

    {currentQuestion.answers.map((answer, index) => (
      <div key={index} className="d-flex mt-1 align-items-center">
        <input
          type="radio"
          name="answer"
          id={`answer-${index}`}
          value={index}
          className="form-check-input"
          checked={selectedAnswer === index}
          onChange={() => setSelectedAnswer(index)}
        />
        <label
          className="form-check-label"
          htmlFor={`answer-${index}`}
        >
          {decodeHtml(answer)}
        </label>
      </div>
    ))}

    <button
      className="btn btn-primary mt-3"
      onClick={handleNext}
      disabled={selectedAnswer === null}
    >
      {currentIndex < questions.length - 1 ? "Next" : "Finish"}
    </button>
  </div>
</div>
  );
};

export default OpenTriviaQuestion;