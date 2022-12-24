package com.example.dto.entityDto.article;


import com.example.dto.entityDto.AttachDto;
import com.example.dto.entityDto.profileDto.ProfileDto;
import com.example.dto.entityDto.RegionDto;
import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ArticleDto {


    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private AttachDto image;
    private Integer regionId;
    private Integer articleTypeId;
    private Integer moderatorId;
    private ArticleStatus status;
    private Integer publisherId;
    private LocalDateTime createdDate;
    private ProfileDto moderator;
    private ProfileDto publisher;
    private RegionDto region;
    private ArticleTypeDto articleType;
    private Integer categoryId;
    private Integer likeCount;
}
