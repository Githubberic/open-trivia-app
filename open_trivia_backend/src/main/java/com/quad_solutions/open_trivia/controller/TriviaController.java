package com.quad_solutions.open_trivia.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.quad_solutions.open_trivia.dto.AnswerDTO;
import com.quad_solutions.open_trivia.dto.QuestionDTO;
import com.quad_solutions.open_trivia.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TriviaController
{

    @Autowired
    private TriviaService triviaService;

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions() {
        try
        {

        List<QuestionDTO> questions = triviaService.getQuestions();

        if(questions.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found");
        }

        return ResponseEntity.ok(questions);

        } catch(JsonProcessingException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing trivia data");
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<?> checkAnswers(@RequestBody List<AnswerDTO> request) {

        if(request.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No answers found");
        }

        int score = triviaService.checkAnswers(request);
        int total = request.size();

        return ResponseEntity.ok().body("You scored " + score + " out of " + total);

    }

}
