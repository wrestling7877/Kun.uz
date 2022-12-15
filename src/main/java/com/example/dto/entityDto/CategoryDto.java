package com.example.dto.entityDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {


    private Integer id;

    @NotBlank(message = "Field must have some value")
    private String key;

    @NotBlank(message = "Field must have some value")
    private String nameUz;

    @NotBlank(message = "Field must have some value")
    private String nameRu;

    @NotBlank(message = "Field must have some value")
    private String nameEn;


    private Boolean visible;


    private LocalDateTime createdDate;
}
