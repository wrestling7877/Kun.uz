package com.example.dto.entityDto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDto {

    private Integer id;
    @NotBlank
    private String key;
    @Size(min = 2,message = "the words mustn't be  less than 2")
    private String nameUz;
    @Size(min = 2,message = "the words mustn't be  less than 2")
    private String nameRu;
    @Size(min = 2,message = "the words mustn't be  less than 2")
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
}
