package com.example.service;

import com.example.dto.entityDto.CategoryDto;
import com.example.entity.CategoryEntity;
import com.example.repository.article.ArticleRepository;
import com.example.repository.AttachRepository;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public CategoryDto create(CategoryDto dto) {

        CategoryEntity entity = toENTITY(dto);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public CategoryEntity toENTITY(CategoryDto dto) {

        CategoryEntity entity = new CategoryEntity();
        entity.setVisible(true);
        entity.setKey(dto.getKey());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        return entity;
    }
}
