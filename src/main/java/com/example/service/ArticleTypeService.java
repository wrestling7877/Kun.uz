package com.example.service;

import com.example.dto.entityDto.article.ArticleTypeDto;
import com.example.entity.article.ArticleTypeEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleTypeMap;
import com.example.repository.ArticleTypeRepository;
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
public class ArticleTypeService {

    EmailHistoryService emailHistoryService = new EmailHistoryService();

    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDto create (ArticleTypeDto dto){

        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
        articleTypeEntity.setCreatedDate(LocalDateTime.now());
        articleTypeEntity.setKey(dto.getKey());
        articleTypeEntity.setVisible(true);
        articleTypeEntity.setNameUz(dto.getNameUz());
        articleTypeEntity.setNameRu(dto.getNameRu());
        articleTypeEntity.setNameEn(dto.getNameEn());

        articleTypeRepository.save(articleTypeEntity);
        dto.setId(articleTypeEntity.getId());

        return dto;
    }

    public ArticleTypeDto update(ArticleTypeDto dto){


        articleTypeRepository.findById(dto.getId()).orElseThrow(()->{
            throw new ItemNotFoundException("id not found ");
        });
        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
        articleTypeEntity.setId(dto.getId());
        articleTypeEntity.setCreatedDate(LocalDateTime.now());
        articleTypeEntity.setKey(dto.getKey());
        articleTypeEntity.setVisible(dto.getVisible());
        articleTypeEntity.setNameUz(dto.getNameUz());
        articleTypeEntity.setNameRu(dto.getNameRu());
        articleTypeEntity.setNameEn(dto.getNameEn());

        articleTypeRepository.save(articleTypeEntity);
        dto.setId(articleTypeEntity.getId());

        return dto;
    }


    public String delete (Integer id){


        articleTypeRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("id not found ");
        });

        articleTypeRepository.deleteById(id);

        return "Deleted was successful";
    }


    public Page<ArticleTypeDto> getList (Integer page, Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(pageable);
        List<ArticleTypeEntity> entityList = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();
         List<ArticleTypeDto> contentList = new LinkedList<>();

        for (ArticleTypeEntity articleTypeEntity : entityList) {
            ArticleTypeDto dto = new ArticleTypeDto();
            dto.setId(articleTypeEntity.getId());
            dto.setKey(articleTypeEntity.getKey());
            dto.setVisible(articleTypeEntity.getVisible());
            dto.setNameRu(articleTypeEntity.getNameRu());
            dto.setNameEn(articleTypeEntity.getNameEn());
            dto.setNameUz(articleTypeEntity.getNameUz());
            contentList.add(dto);

        }

        return new PageImpl<>(contentList,pageable,totalElement);
    }


    public List<ArticleTypeMap> getByLang(String lang) {


        List<ArticleTypeMap> mappers = new LinkedList<>();

        if (lang.equals("en")) {
            mappers = articleTypeRepository.findByEn();

        } else if (lang.equals("uz")) {
            mappers = articleTypeRepository.findByUz();

        } else if (lang.equals("ru")){
            mappers = articleTypeRepository.findByRu();
    }else {
            throw new ItemNotFoundException("Language not found");
        }



        return mappers;
    }


    public ArticleTypeDto toDTO(ArticleTypeEntity entity){

        ArticleTypeDto dto = new ArticleTypeDto();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setVisible(entity.getVisible());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setNameUz(entity.getNameUz());

        return dto;
    }
}
