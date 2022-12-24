package com.example.dto.entityDto.article;

import com.example.dto.entityDto.AttachDto;
import com.example.dto.entityDto.CategoryDto;
import com.example.dto.entityDto.RegionDto;
import com.example.dto.entityDto.profileDto.ProfileDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class ArticleFilterFullInfoDto {

    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private RegionDto region;
    private Integer likeCount;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private CategoryDto categoryDto;
}
