package com.example.service;

import com.example.dto.entityDto.RegionDto;
import com.example.dto.jwt.JwtDto;
import com.example.entity.RegionEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.RegionMap;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public RegionDto create(RegionDto dto) {

        RegionEntity entity = new RegionEntity();
        entity.setCreatedDate(LocalDateTime.now());
        entity.setKey(dto.getKey());
        entity.setVisible(dto.getVisible());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());

        regionRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public RegionDto update(RegionDto dto) {


        regionRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new ItemNotFoundException("id not found ");
        });
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(dto.getId());
        regionEntity.setCreatedDate(LocalDateTime.now());
        regionEntity.setKey(dto.getKey());
        regionEntity.setVisible(dto.getVisible());
        regionEntity.setNameUz(dto.getNameUz());
        regionEntity.setNameRu(dto.getNameRu());
        regionEntity.setNameEn(dto.getNameEn());

        regionRepository.save(regionEntity);
        dto.setId(regionEntity.getId());

        return dto;
    }


    public String delete(Integer id) {


        regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("id not found ");
        });

        regionRepository.deleteById(id);

        return "Deleted was successful";
    }


    public Page<RegionDto> getList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RegionEntity> pageObj = regionRepository.findAll(pageable);
        List<RegionEntity> entityList = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();
        List<RegionDto> contentList = new LinkedList<>();

        for (RegionEntity entity : entityList) {
            RegionDto dto = new RegionDto();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setVisible(entity.getVisible());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setNameUz(entity.getNameUz());
            contentList.add(dto);

        }

        return new PageImpl<>(contentList, pageable, totalElement);
    }


    public List<RegionMap> getByLang(String lang) {


        List<RegionMap> mappers = new LinkedList<>();

        if (lang.equals("en")) {
            mappers = regionRepository.findByEn();

        } else if (lang.equals("uz")) {
            mappers = regionRepository.findByUz();

        } else if (lang.equals("ru")){
            mappers = regionRepository.findByRu();
        }else {
            throw new ItemNotFoundException("Language not found");
        }



        return mappers;
    }


    public RegionDto toDTO(RegionEntity entity){

        RegionDto dto = new RegionDto();

        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
