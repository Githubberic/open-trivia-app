package com.quad_solutions.open_trivia.dto;

import java.util.UUID;

public class AnswerDTO {
    private UUID questionId;
    private String answer;

    public AnswerDTO(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }
}
