package com.example.repository;

import com.example.entity.RegionEntity;
import com.example.mapper.RegionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {

  //  @Query("select new com.example.mapper.RegionMapper(r.id,r.key,r.nameEn) from RegionEntity r ")
    @Query(value = "select r.id as rId,r.key as rKey,r.name_en as rName from region r",nativeQuery = true)
    List<RegionMap> findByEn();

  @Query(value = "select r.id as rId,r.key as rKey,r.name_uz as rName from region r",nativeQuery = true)
  List<RegionMap> findByUz();

  @Query(value = "select r.id as rId,r.key as rKey,r.name_ru as rName from region r",nativeQuery = true)
  List<RegionMap> findByRu();
}
