package com.quad_solutions.open_trivia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quad_solutions.open_trivia.dto.AnswerDTO;
import com.quad_solutions.open_trivia.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TriviaService {

    //Maak ObjectMapper aan voor het converteren
    @Autowired
    private ObjectMapper objectMapper;

    //Haal api url op uit de application.properties
    @Value("${opentrivia.api.url}")
    private String triviaApiUrl;

    //Maak opslag voor de correcte antwoorden
    private Map<UUID, String> correctAnswers = new HashMap<>();

    public List<QuestionDTO> getQuestions() throws JsonProcessingException {
        //Maak RestTemplate aan voor het uitvoeren van de GET request
        RestTemplate restTemplate = new RestTemplate();

        //Haal de vragen op uit de Open Trivia API en sla ze op in een string
        String questionsString = restTemplate.getForObject(triviaApiUrl, String.class);

        //Converteer de string naar een map
        Map<String, Object> questionsMap = objectMapper.readValue(questionsString, Map.class);

        //Maak een lijst aan met de opgehaalde resultaten
        List<Map<String, Object>> results = (List<Map<String, Object>>) questionsMap.getOrDefault("results", Collections.emptyList());

        //Maak een lijst aan voor de vragen
        List<QuestionDTO> questions = new ArrayList<>();

        //Map de resultaten aan de lijst voor de vragen
        for (Map<String, Object> result : results) {

            UUID id = UUID.randomUUID();
            String question = (String) result.get("question");
            String correctAnswer = (String) result.get("correct_answer");

            //Voeg alle mogelijke antwoorden toe aan één lijst
            List<String> answers = new ArrayList<>((List<String>) result.get("incorrect_answers"));
            answers.add(correctAnswer);

            //Schud de antwoorden zodat de correcte niet altijd laatste staat
            Collections.shuffle(answers);

            //Sla het correcte antwoord op
            correctAnswers.put(id, correctAnswer);

            questions.add(new QuestionDTO(id, question, answers));
        }

        return questions;

    }

    public int checkAnswers(List<AnswerDTO> userAnswers) {
        int score = 0;

        //Vergelijk de antwoorden van de gebruiker met de daadwerkelijke antwoorden
        for (AnswerDTO userAnswer : userAnswers) {
            String correctAnswer = correctAnswers.get(userAnswer.getQuestionId());
            if (correctAnswer != null && correctAnswer.equals(userAnswer.getAnswer())) {
                score++;
            }
        }
        return score;
    }

    public Map<UUID, String> getCorrectAnswers() {
        return correctAnswers;
    }
}
