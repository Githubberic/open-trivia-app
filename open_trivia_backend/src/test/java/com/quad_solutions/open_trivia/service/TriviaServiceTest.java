package com.quad_solutions.open_trivia.service;

import com.quad_solutions.open_trivia.dto.AnswerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class TriviaServiceTest {

    private TriviaService triviaService;

    private UUID questionId1;
    private UUID questionId2;

    @BeforeEach
    void setUp() {
        triviaService = new TriviaService();

        //Voeg test data toe aan correctAnswers map
        questionId1 = UUID.randomUUID();
        questionId2 = UUID.randomUUID();

        triviaService.getCorrectAnswers().put(questionId1, "Correct antwoord 1");
        triviaService.getCorrectAnswers().put(questionId2, "Correct antwoord 2");
    }

    @Test
    void testCheckAnswersWithCorrectAnswers() {

        //Maak nieuwe antwoordenlijst aan met questionId1 en het antwoord van gebruiker
        List<AnswerDTO> answers = List.of(
                new AnswerDTO(questionId1,
                        "Correct antwoord 1")
        );

        int score = triviaService.checkAnswers(answers);

        assertEquals(1, score);

    }

    @Test
    void testCheckAnswersWithWrongAnswers() {
        List<AnswerDTO> answers = List.of(
                new AnswerDTO(questionId1,
                        "Fout antwoord")
        );

        int score = triviaService.checkAnswers(answers);

        assertEquals(0, score);
    }

    @Test
    void testCheckAnswersEmptyList() {
        assertEquals(0, triviaService.checkAnswers(Collections.emptyList()));
    }


}
