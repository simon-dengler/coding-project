package de.keywork.backend.dto;

import lombok.Data;

@Data
public class FormDataDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String favouriteAnimal;
    private String zodiac;
}
