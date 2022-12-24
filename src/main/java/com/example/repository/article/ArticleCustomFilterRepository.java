package com.example.repository.article;

import com.example.dto.entityDto.article.ArticleFilterDto;
import com.example.entity.article.ArticleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArticleCustomFilterRepository {

    @Autowired
    private EntityManager entityManager;


    public Page<ArticleEntity> getFilterPage(ArticleFilterDto filter, Integer page, Integer size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builder = new StringBuilder("select a from ArticleEntity a where a.visible = true ");

        StringBuilder countBuilder = new StringBuilder("select count(a) from ArticleEntity a where a.visible = true ");

        if (filter.getId() != null) {
            builder.append(" and a.id =:id ");
            countBuilder.append(" and a.id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getTitle() != null) {
            builder.append(" and a.title =:title ");
            countBuilder.append(" and a.title =:title ");
            params.put("title", filter.getTitle());
        }
        if (filter.getRegionId() != null) {
            builder.append(" and a.regionId =:regionId ");
            countBuilder.append(" and a.regionId =:regionId ");
            params.put("regionId", filter.getRegionId());
        }
        if (filter.getCategoryId() != null) {
            builder.append(" and a.categoryId =:categoryId ");
            countBuilder.append(" and a.categoryId =:categoryId ");
            params.put("categoryId", filter.getCategoryId());
        }
        if (filter.getModeratorId() != null) {
            builder.append(" and a.moderatorId =:moderatorId ");
            countBuilder.append(" and a.moderatorId =:moderatorId ");
            params.put("moderatorId", filter.getModeratorId());
        }
        if (filter.getPublisherId() != null) {
            builder.append(" and a.publisherId =:publisherId ");
            countBuilder.append(" and a.publisherId =:publisherId ");
            params.put("publisherId", filter.getPublisherId());
        }
        if (filter.getStatus() != null) {
            builder.append(" and a.status =:status ");
            countBuilder.append(" and a.status =:status ");
            params.put("status", filter.getStatus());
        }

        ////////////////////////////////////////

        if (filter.getFromDate() != null && filter.getToDate() != null) {
            builder.append(" and a.createdDate as date between :fromDate and  :toDate");
            countBuilder.append(" and a.createdDate as date between :fromDate and  :toDate");
            params.put("fromDate", filter.getFromDate());
            params.put("toDate", filter.getToDate());
        }else if (filter.getFromDate() != null){
            builder.append(" and  a.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
            countBuilder.append(" and  a.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
            params.put("fromDate",filter.getFromDate().atStartOfDay());

        }else if (filter.getToDate() != null){
            builder.append(" and  a.createdDate <= :toDate ");
            countBuilder.append(" and  a.createdDate <= :toDate "); //2022-12-14 59:59:9999999
            params.put("toDate",LocalDateTime.of(filter.getToDate(),LocalTime.MAX));

        }

        //////////////////////

        if (filter.getFromPublishedDate() != null && filter.getToPublishedDate() != null) {
            builder.append(" and a.publishedDate as date between :fromPublishDate and  :toPublishDate");
            countBuilder.append(" and a.publishedDate as date between :toPublishDate and  :toPublishDate");
            params.put("fromPublishDate", filter.getFromPublishedDate());
            params.put("toPublishDate", filter.getToPublishedDate());
        }else if (filter.getFromPublishedDate() != null){
            builder.append(" and  a.publishedDate >= :fromPublishDate "); // 2022-12-14 00:00:0000
            countBuilder.append(" and  a.publishedDate >= :fromPublishDate "); // 2022-12-14 00:00:0000
            params.put("fromPublishDate",filter.getFromPublishedDate().atStartOfDay());

        }else if (filter.getToPublishedDate() != null){
            builder.append(" and  a.publishedDate <= :toPublishDate ");
            countBuilder.append(" and  a.publishedDate <= :toPublishDate "); //2022-12-14 59:59:9999999
            params.put("toPublishDate",LocalDateTime.of(filter.getToPublishedDate(),LocalTime.MAX));

        }


        Query query = this.entityManager.createQuery(builder.toString());

        query.setFirstResult((page) * size);
        query.setMaxResults(size);

        for (Map.Entry<String,Object> entry : params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }

        List articleEntityList = query.getResultList();

        query = this.entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String,Object> entry : params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
        Long totalElement = (Long) query.getSingleResult();

        return new PageImpl<>(articleEntityList, PageRequest.of(page,size),totalElement);
    }
}
