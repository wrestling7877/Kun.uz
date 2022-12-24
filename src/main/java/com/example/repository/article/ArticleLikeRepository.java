package com.example.repository.article;


import com.example.entity.article.ArticleLikeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity,Integer> {


    ArticleLikeEntity findByIdAndArticleId(Integer id, String articleId);

}
