package com.example.dto.entityDto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentCreateDto {

    // (content,article_id,reply_id)
    @NotNull
    private Integer replyId;

    @NotBlank
    private String articleId;

    @NotBlank
    private String content;






}
