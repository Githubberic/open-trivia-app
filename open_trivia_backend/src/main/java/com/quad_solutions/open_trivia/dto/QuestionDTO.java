package com.quad_solutions.open_trivia.dto;

import java.util.List;
import java.util.UUID;

public class QuestionDTO {
    private UUID id;
    private String question;
    private List<String> answers;

    public QuestionDTO(UUID id, String question, List<String> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
