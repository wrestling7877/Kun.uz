package com.example.entity.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "articleType")
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String key;

    @Column
    private String nameUz;

    @Column
    private String nameRu;

    @Column
    private String nameEn;


    @Column
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
