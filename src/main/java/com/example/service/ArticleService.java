package com.example.service;

import com.example.dto.entityDto.article.ArticleCreatedDto;
import com.example.dto.entityDto.article.ArticleDto;
import com.example.entity.article.ArticleEntity;

import com.example.dto.entityDto.article.ArticleShortInfoDTO;
import com.example.enums.ArticleStatus;
import com.example.exp.ForbiddenException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleFullInfo;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.article.ArticleRepository;
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
import java.util.Optional;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private AttachService attachService;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    @Autowired
    private ArticleTypeService articleTypeService;

    @Autowired
    private ProfileService profileService;


    @Autowired
    private RegionService regionService;

    public ArticleDto create(ArticleCreatedDto dto, Integer moderatorId) {

        ArticleEntity entity = new ArticleEntity();
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(moderatorId);
        entity.setRegionId(dto.getRegionId());
        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setImageId(dto.getImageId());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setSharedCount(0);
        entity.setViewCount(0);
        entity.setVisible(true);

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        entity.setCreatedDate(LocalDateTime.now());
        articleRepository.save(entity);

        ArticleDto responseDTO = new ArticleDto();
        responseDTO.setId(entity.getId());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setContent(entity.getContent());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setCategoryId(entity.getCategoryId());

        return responseDTO;
    }


    public ArticleDto update(ArticleCreatedDto dto) {

        Optional<ArticleEntity> optionalEntity = articleRepository.findById(dto.getId());
        ArticleEntity entity = optionalEntity.get();
        if (!entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            throw new ForbiddenException("Method not allowed");
        }

        entity.setRegionId(dto.getRegionId());
        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setImageId(dto.getImageId());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        articleRepository.save(entity);

        ArticleDto responseDTO = new ArticleDto();
        responseDTO.setId(entity.getId());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setContent(entity.getContent());
        responseDTO.setCreatedDate(entity.getCreatedDate());

        return responseDTO;
    }


    public List<ArticleShortInfoDTO> getLast8(List<String> idList) {

        List<ArticleShortInfoMapper> list = articleRepository.getLast8Native(ArticleStatus.PUBLISHED, idList);

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setImage(attachService.getById(entity.getImageId()));
            dtoList.add(dto);
        });

        return dtoList;
    }

    public String delete(String id) {

        articleRepository.deleteById(id);

        return "deleted was successful";
    }

    public Boolean changeStatus(String id, Integer moderatorId) {

        Optional<ArticleEntity> articleEntity = articleRepository.findById(id);
        articleEntity.get().setStatus(ArticleStatus.PUBLISHED);
        articleEntity.get().setModeratorId(moderatorId);
        articleRepository.save(articleEntity.get());
        return true;
    }


    public List<ArticleShortInfoDTO> getLast5(Integer typeId) {


        Page<ArticleEntity> list1 = articleRepository.findTopByArticleType5(typeId, ArticleStatus.PUBLISHED, PageRequest.of(0, 5));
        List<ArticleEntity> list = list1.getContent();

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setImage(attachService.getById(entity.getImageId()));
            dto.setTypeId(entity.getArticleTypeId());
            dtoList.add(dto);
        });

        return dtoList;
    }

    public List<ArticleShortInfoDTO> getLast3(Integer typeId) {


        Page<ArticleEntity> list1 = articleRepository.findTopByArticleType3(typeId, ArticleStatus.PUBLISHED, PageRequest.of(0, 3));
        List<ArticleEntity> list = list1.getContent();

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setImage(attachService.getById(entity.getImageId()));
            dto.setTypeId(entity.getArticleTypeId());
            dtoList.add(dto);
        });

        return dtoList;
    }


    public List<ArticleFullInfo> getByLang(String lang, String id) {


        List<ArticleFullInfo> mappers = new LinkedList<>();

        if (lang.equals("en")) {
            mappers = articleRepository.findByEn(id);

        } else if (lang.equals("uz")) {
            mappers = articleRepository.findByUz(id);

        } else if (lang.equals("ru")) {
            mappers = articleRepository.findByRu(id);
        } else {
            throw new ItemNotFoundException("Language not found");
        }


        return mappers;
    }


    public ArticleDto toDTO(ArticleEntity entity) {

        ArticleDto dto = new ArticleDto();

        dto.setId(entity.getId());
        dto.setArticleType(articleTypeService.toDTO(entity.getArticleType()));
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setImage(attachService.getById(entity.getImageId()));
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setTitle(entity.getTitle());
        dto.setModerator(profileService.toDTO(entity.getModerator()));
        dto.setPublisher(profileService.toDTO(entity.getPublisher()));
        dto.setRegion(regionService.toDTO(entity.getRegion()));
        dto.setSharedCount(entity.getSharedCount());
        return dto;
    }


    public ArticleShortInfoDTO toShortToDTO(ArticleEntity entity) {

        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();

        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setImage(attachService.getById(entity.getImageId()));
        dto.setTitle(entity.getTitle());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    public List<ArticleShortInfoDTO> getLast4() {

        Page<ArticleEntity> entityList1 = articleRepository.findTopByOOrderByViewCountDesc(PageRequest.of(0, 4));
        List<ArticleEntity> entityList = entityList1.getContent();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortToDTO(entity));
        }

        return dtoList;
    }

    public List<ArticleShortInfoDTO> getLast5ByRegion(String key, Integer typeId) {

        List<ArticleShortInfoMapper> mappers = articleRepository.getLast5ByKey(key, typeId);
        List<ArticleShortInfoDTO> shortInfoDTOList = new LinkedList<>();
        for (ArticleShortInfoMapper mapper : mappers) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setImage(attachService.getById(mapper.getImageId()));
            dto.setDescription(mapper.getDescription());
            dto.setPublishedDate(mapper.getPublishedDate());
            dto.setTitle(mapper.getTitle());
            shortInfoDTOList.add(dto);

        }
        return shortInfoDTOList;
    }


    public Page<ArticleShortInfoDTO> getPageByRegionKey(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getPageByRegionKey(key, PageRequest.of(page, size));
        List<ArticleShortInfoMapper> mappers = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();

        List<ArticleShortInfoDTO> shortInfoDTOList = new LinkedList<>();
        for (ArticleShortInfoMapper mapper : mappers) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setImage(attachService.getById(mapper.getImageId()));
            dto.setDescription(mapper.getDescription());
            dto.setPublishedDate(mapper.getPublishedDate());
            dto.setTitle(mapper.getTitle());
            shortInfoDTOList.add(dto);

        }
        return new PageImpl<>(shortInfoDTOList, pageable, totalElement);
    }


    public List<ArticleShortInfoDTO> getListByCategory(String key) {

        List<ArticleShortInfoMapper> mappers = articleRepository.getListByCategoryKey(key);

        List<ArticleShortInfoDTO> shortInfoDTOList = new LinkedList<>();

        for (ArticleShortInfoMapper mapper : mappers) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setImage(attachService.getById(mapper.getImageId()));
            dto.setDescription(mapper.getDescription());
            dto.setPublishedDate(mapper.getPublishedDate());
            dto.setTitle(mapper.getTitle());
            shortInfoDTOList.add(dto);

        }
        return shortInfoDTOList;
    }


    public Page<ArticleShortInfoDTO> getPageByCategoryKey(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pageObj = articleRepository.getPageByCategoryKey(key, PageRequest.of(page, size));
        List<ArticleShortInfoMapper> mappers = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();

        List<ArticleShortInfoDTO> shortInfoDTOList = new LinkedList<>();
        for (ArticleShortInfoMapper mapper : mappers) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setImage(attachService.getById(mapper.getImageId()));
            dto.setDescription(mapper.getDescription());
            dto.setPublishedDate(mapper.getPublishedDate());
            dto.setTitle(mapper.getTitle());
            shortInfoDTOList.add(dto);

        }
        return new PageImpl<>(shortInfoDTOList, pageable, totalElement);
    }



}
