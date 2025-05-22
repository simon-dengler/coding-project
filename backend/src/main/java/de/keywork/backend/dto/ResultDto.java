package de.keywork.backend.dto;

import lombok.Data;

@Data
public class ResultDto {
    private Long id;
    private String category;
    private String question;
    private String correctAnswer;
    private boolean answeredCorrectly;
    private Long formDataId;
}
