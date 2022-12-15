package com.example.repository;

import com.example.dto.entityDto.profileDto.ProfileFilterDto;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDto filter) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("Select p from ProfileEntity p ");
        builder.append(" where p.visible = true ");

        if (filter.getName() != null) {

            builder.append(" and p.name =: name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {

            builder.append(" and p.surname =:surname ");
            params.put("surname", filter.getSurname());
        }
        if (filter.getPhone() != null) {

            builder.append(" and p.phone =:phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getStatus() != null) {

            builder.append(" and p.status =:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            // (SELECT cast(created_date as date))=?1",
            // to_date(to_char(created_date, 'YYYY/MM/DD'), 'YYYY/MM/DD')=?1
            builder.append(" and  cast(p.createdDate as date) between :fromDate and :toDate ");
            params.put("fromDate", filter.getFromDate());
            params.put("toDate", filter.getToDate());

        } else if (filter.getFromDate() != null) {

            builder.append(" and  p.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
            params.put("fromDate", filter.getFromDate().atStartOfDay());

        } else if (filter.getToDate() != null) {

            builder.append(" and  p.createdDate <= :fromDate "); // 2022-12-14 59:59:9999999
            params.put("fromDate", LocalDateTime.of(filter.getFromDate(), LocalTime.MAX));

        }


        Query query = this.entityManager.createQuery(builder.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<ProfileEntity> profileEntities = query.getResultList();
        return profileEntities;
    }

    //    public Page<ProfileEntity> filterPage(ProfileFilterDto filter, int page, int size) {
//        Map<String, Object> params = new HashMap<>();
//        StringBuilder builder = new StringBuilder("Select p from ProfileEntity p ");
//        builder.append(" where 1 = 1 ");
//
//        StringBuilder countBuilder = new StringBuilder("Select count(p) from ProfileEntity p where 1 = 1  ");
//
//        if (filter.getName() != null) {
//            builder.append(" and p.name =:name ");
//            countBuilder.append(" and p.name =:name ");
//            params.put("name", filter.getName());
//
//        }
//
//        if (filter.getSurname() != null) {
//            builder.append(" and p.surname =:surname ");
//            countBuilder.append(" and p.surname =:surname ");
//            params.put("surname", filter.getSurname());
//        }
//        if (filter.getPhone() != null) {
//            builder.append(" and p.phone =:phone ");
//            countBuilder.append(" and p.phone =:phone ");
//            params.put("phone", filter.getPhone());
//        }
//
//        if (filter.getStatus() != null) {
//            builder.append(" and p.status =:status ");
//            countBuilder.append(" and p.status =:status ");
//            params.put("status", filter.getStatus());
//        }
//
//        if (filter.getRole() != null) {
//            builder.append(" and p.role in (:role) ");
//            countBuilder.append(" and p.role in (:role) ");
//            params.put("role", filter.getRole());
//        }
//
//        if (filter.getFromDate() != null && filter.getToDate() != null) {
//            // (SELECT cast(created_date as date))=?1",
//            // to_date(to_char(created_date, 'YYYY/MM/DD'), 'YYYY/MM/DD')=?1
//            builder.append(" and  cast(p.createdDate as date) between :fromDate and :toDate ");
//            countBuilder.append(" and  cast(p.createdDate as date) between :fromDate and :toDate ");
//            params.put("fromDate", filter.getFromDate());
//            params.put("toDate", filter.getToDate());
//        } else if (filter.getFromDate() != null) {
//            builder.append(" and  p.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
//            countBuilder.append(" and  p.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
//            params.put("fromDate", filter.getFromDate().atStartOfDay());
//        } else if (filter.getToDate() != null) {
//            builder.append(" and  p.createdDate <= :fromDate "); // 2022-12-14 59:59:9999999
//            countBuilder.append(" and  p.createdDate <= :fromDate "); // 2022-12-14 59:59:9999999
//            params.put("fromDate", LocalDateTime.of(filter.getFromDate(), LocalTime.MAX));
//        }
//
//        //content
//        Query query = this.entityManager.createQuery(builder.toString());
//        query.setFirstResult((page) * size); // offset 40
//        query.setMaxResults(size); // limit 20
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//        }
//        List<ProfileEntity> profileList = query.getResultList();
//
//        // totalCount
//        query = this.entityManager.createQuery(countBuilder.toString());
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//        }
//        Long totalCount = (Long) query.getSingleResult();
//
//        return new PageImpl<>(profileList, PageRequest.of(page, size), totalCount);
//    }
//
    public Page<ProfileEntity> filterPage(ProfileFilterDto filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builder = new StringBuilder("Select p from ProfileEntity p ");
        builder.append(" where p.visible = true ");

        StringBuilder countBuilder = new StringBuilder("Select count(p) from ProfileEntity p where p.visible = true ");

        if (filter.getName() != null) {
            builder.append(" and p.name =:name ");
            countBuilder.append(" and p.name =:name ");
            params.put("name", filter.getName());

        }

        if (filter.getSurname() != null) {
            builder.append(" and p.surname =:surname ");
            countBuilder.append(" and p.surname =:surname ");
            params.put("surname", filter.getSurname());
        }
        if (filter.getPhone() != null) {
            builder.append(" and p.phone =:phone ");
            countBuilder.append(" and p.phone =:phone ");
            params.put("phone", filter.getPhone());
        }

        if (filter.getStatus() != null) {
            builder.append(" and p.status =:status ");
            countBuilder.append(" and p.status =:status ");
            params.put("status", filter.getStatus());
        }

        if (filter.getRole() != null) {
            builder.append(" and p.role in (:role) ");
            countBuilder.append(" and p.role in (:role) ");
            params.put("role", filter.getRole());
        }

        if (filter.getFromDate() != null && filter.getToDate() != null) {
            // (SELECT cast(created_date as date))=?1",
            // to_date(to_char(created_date, 'YYYY/MM/DD'), 'YYYY/MM/DD')=?1
            builder.append(" and  p.createdDate as date between :fromDate and :toDate ");
            countBuilder.append(" and p.createdDate as date between :fromDate and :toDate ");
            params.put("fromDate", filter.getFromDate());
            params.put("toDate", filter.getToDate());
        } else if (filter.getFromDate() != null) {
            builder.append(" and  p.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
            countBuilder.append(" and  p.createdDate >= :fromDate "); // 2022-12-14 00:00:0000
            params.put("fromDate", filter.getFromDate().atStartOfDay());
        } else if (filter.getToDate() != null) {
            builder.append(" and  p.createdDate <= :toDate "); // 2022-12-14 59:59:9999999
            countBuilder.append(" and  p.createdDate <= :toDate "); // 2022-12-14 59:59:9999999
            params.put("toDate", LocalDateTime.of(filter.getToDate(), LocalTime.MAX));
        }

        //content
        Query query = this.entityManager.createQuery(builder.toString());
        query.setFirstResult((page) * size); // offset 40
        query.setMaxResults(size); // limit 20
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<ProfileEntity> profileList = query.getResultList();

        // totalCount
        query = this.entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl<>(profileList, PageRequest.of(page, size), totalCount);
    }


    public List<ProfileEntity> getAll() {
        Query query = this.entityManager.createQuery("SELECT p From ProfileEntity as p");
        List profileEntities = query.getResultList();
        return profileEntities;
    }

    public List<ProfileEntity> getAllNative() {
        Query query = entityManager.createNativeQuery("SELECT * FROM profile ", ProfileEntity.class);
        List profileEntities = query.getResultList();
        return profileEntities;
    }
}
