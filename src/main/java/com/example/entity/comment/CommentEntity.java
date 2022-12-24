package com.example.entity.comment;

import com.example.entity.ProfileEntity;
import com.example.entity.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private LocalDate createdDate = LocalDate.now();


    @Column
    private LocalDate updateDate;


    @Column(columnDefinition = "text")
    private String content;

    @Column
    private Boolean visible = Boolean.TRUE;


    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;


    @Column
    private Integer replyId;


    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false, insertable = false)
    private ArticleEntity article;


}
