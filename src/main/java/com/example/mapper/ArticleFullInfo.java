package com.example.mapper;

import com.example.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFullInfo {


    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private String imageId;
    private Integer regionId;
    private Integer articleTypeId;
    private Integer moderatorId;
    private ArticleStatus status;
    private Integer publisherId;
    private LocalDateTime createdDate;


    private Integer tId;


    private String tKey;


    private String tName;


}
