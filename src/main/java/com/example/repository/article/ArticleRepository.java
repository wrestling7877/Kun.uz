package com.example.repository.article;

import com.example.entity.article.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleFullInfo;
import com.example.mapper.ArticleShortInfoMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {


    @Query(value = "select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.published_date as publishedDate " +
            " from article  a  where a.status =?1 and a.visible =true and a.id not in (?2) order by a.published_date desc limit 8",
            nativeQuery = true)
    List<ArticleShortInfoMapper> getLast8Native(ArticleStatus status, List<String> idList);


    @Query("from ArticleEntity as a where  a.articleTypeId = ?1 and a.status = ?2 order by a.createdDate desc  ")
    Page<ArticleEntity> findTopByArticleType5(Integer articleTypeId, ArticleStatus status, Pageable pageable);


    @Query("from ArticleEntity as a where  a.articleTypeId = ?1 and a.status = ?2 order by a.createdDate desc  ")
    Page<ArticleEntity> findTopByArticleType3(Integer articleTypeId, ArticleStatus status, Pageable pageable);

    @Query("select new com.example.mapper.ArticleFullInfo(a.id,a.title,a.description,a.content,a.sharedCount," +
            "a.imageId,a.regionId,a.articleTypeId,a.moderatorId,a.status,a.publisherId,a.createdDate,t.id,t.key,t.nameEn)" +
            " from ArticleEntity as a inner join a.articleType as t where a.id = ?1")
    List<ArticleFullInfo> findByEn(String id);


    @Query("select new com.example.mapper.ArticleFullInfo(a.id,a.title,a.description,a.content,a.sharedCount," +
            "a.imageId,a.regionId,a.articleTypeId,a.moderatorId,a.status,a.publisherId,a.createdDate,t.id,t.key,t.nameUz)" +
            " from ArticleEntity as a inner join a.articleType as t where a.id = ?1")
    List<ArticleFullInfo> findByUz(String id);


    @Query("select new com.example.mapper.ArticleFullInfo(a.id,a.title,a.description,a.content,a.sharedCount," +
            "a.imageId,a.regionId,a.articleTypeId,a.moderatorId,a.status,a.publisherId,a.createdDate,t.id,t.key,t.nameRu)" +
            " from ArticleEntity as a inner join a.articleType as t where a.id = ?1")
    List<ArticleFullInfo> findByRu(String id);


    @Query("from ArticleEntity as a order by a.viewCount desc ")
    Page<ArticleEntity> findTopByOOrderByViewCountDesc(Pageable pageable);

//    select article.* from article JOIN article_type t ON t.id = article.article_type_id JOIN region r ON r.id = article.region_id
//    where t.id =3 and r.key= 'key2' ORDER BY article.created_date DESC limit 5

    @Query(value = " select a.id as id,a.title as title,a.description as description, a.image_id as imageId, a.published_date as publishedDate from article  a JOIN article_type t ON t.id = a.article_type_id JOIN region r ON r.id = a.region_id" +
            " where t.id =?2 and r.key= ?1 ORDER BY a.created_date DESC limit 5", nativeQuery = true)
    List<ArticleShortInfoMapper> getLast5ByKey(String key, Integer typeId);


    @Query(value = " select a.id as id,a.title as title,a.description as description, a.image_id as imageId, a.published_date as publishedDate from article  a JOIN region r ON r.id = a.region_id" +
            " where r.key= ?1 ", nativeQuery = true)
    Page<ArticleShortInfoMapper> getPageByRegionKey(String key, Pageable pageable);



    @Query(value = " select a.id as id,a.title as title,a.description as description, a.image_id as imageId, a.published_date as publishedDate" +
            " from article as a join category as c on c.id = a.category_id where c.key= ?1", nativeQuery = true)
    List<ArticleShortInfoMapper> getListByCategoryKey(String key);


    @Query(value = " select a.id as id,a.title as title,a.description as description, a.image_id as imageId, a.published_date as publishedDate"+
            " from article as a join category as c on c.id = a.category_id where c.key= ?1", nativeQuery = true)
    Page<ArticleShortInfoMapper> getPageByCategoryKey(String key, Pageable pageable);


    ArticleEntity getById (String id);
}
