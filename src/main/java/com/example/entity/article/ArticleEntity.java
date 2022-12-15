package com.example.entity.article;

import com.example.entity.AttachEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class  ArticleEntity {


    @Id
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String content;

    @Column
    private Integer sharedCount = 0;


    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    private RegionEntity region;


    @Column(name = "article_type_id")
    private Integer articleTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id", updatable = false, insertable = false)
    private ArticleTypeEntity articleType;


    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", updatable = false, insertable = false)
    private ProfileEntity moderator;


    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;


    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", updatable = false, insertable = false)
    private AttachEntity image;


    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private CategoryEntity category;


    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;


    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private LocalDateTime publishedDate = LocalDateTime.now();

    @Column
    private Boolean visible = Boolean.TRUE;

    @Column
    private Integer viewCount = 0;


}
