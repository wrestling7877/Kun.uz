package com.example.dto.entityDto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreatedDto {
    protected String id;
     @Size(min = 10, max = 100, message = "Title should be between 10 and 100")
    private String title;

    @NotBlank(message = "Description must have some value")
    private String description;

    @NotBlank(message = "Content must have some value")
    private String content;

    @NotBlank(message = "You should select image")
    private String imageId;

    @NotNull(message = "You should select region")
    private Integer regionId;

    @NotNull(message = "You should select region")
    private Integer categoryId;



    @NotNull(message = "You should select article type")
    private Integer articleTypeId;


}
