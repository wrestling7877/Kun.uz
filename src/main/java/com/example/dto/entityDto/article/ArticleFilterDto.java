package com.example.dto.entityDto.article;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ArticleFilterDto {

//    Filter Article (id,title,region_id,category_id,crated_date_from,created_date_to
//            published_date_from,published_date_to,moderator_id,publisher_id,status) with Pagination (PUBLISHER)
//    ArticleShortInfo

    private String Id;
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate fromPublishedDate;
    private LocalDate toPublishedDate;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;

}
