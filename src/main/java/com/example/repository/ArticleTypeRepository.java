package com.example.repository;

import com.example.entity.article.ArticleTypeEntity;
import com.example.mapper.ArticleTypeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity,Integer> {



    @Query(value = "select a.id as aId,a.key as aKey,a.name_en as aName from article_type a",nativeQuery = true)
    List<ArticleTypeMap> findByEn();

    @Query(value = "select a.id as aId,a.key as aKey,a.name_uz as aName from article_type a",nativeQuery = true)
    List<ArticleTypeMap> findByUz();

    @Query(value = "select a.id as aId,a.key as aKey,a.name_ru as aName from article_type a",nativeQuery = true)
    List<ArticleTypeMap> findByRu();
}
