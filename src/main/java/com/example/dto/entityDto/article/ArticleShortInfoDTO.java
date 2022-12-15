package com.example.dto.entityDto.article;

import com.example.dto.entityDto.AttachDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {

    private String title;
    private String description;
    private String content;
    private Integer regionId;
    private Integer moderatorId;
    private Integer publisherId;
    private Integer typeId;

    private LocalDateTime publishedDate;

    private AttachDto image;
    private Integer viewCount;


}
