package com.example.dto.entityDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EmailHistoryDto {

    private Integer id;

    @NotBlank
    private String message;

    @Email
    private String email;

    private LocalDateTime createdDate;
}
