package com.example.service;

import com.example.dto.entityDto.EmailHistoryDto;
import com.example.entity.EmailHistoryEntity;
import com.example.entity.EmailHistoryEntityRepository;
import com.example.repository.EmailHistoryRepository;
import com.example.repository.LikeRepository;
import com.example.repository.ProfileRepository;
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
public class EmailHistoryService {


    @Autowired
    private EmailHistoryEntityRepository emailHistoryEntityRepository;


    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private ProfileRepository profileRepository;


    public void create(EmailHistoryDto dto) {

        EmailHistoryEntity entity = new EmailHistoryEntity();

        entity.setEmail(dto.getEmail());
        entity.setMessage(dto.getMessage());
        entity.setCreatedDate(dto.getCreatedDate());
        emailHistoryEntityRepository.save(entity);
    }


    public List<EmailHistoryDto> getByEmail(String email) {

        List<EmailHistoryEntity> emailHistoryEntityList = emailHistoryRepository.findByEmail(email);

        List<EmailHistoryDto> dtoList = new LinkedList<>();

        emailHistoryEntityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }


    public List<EmailHistoryDto> getByCreatedDate(String dateTime) {

        List<EmailHistoryEntity> emailHistoryEntityList = emailHistoryRepository.findByCreatedDate(LocalDateTime.parse(dateTime));

        List<EmailHistoryDto> dtoList = new LinkedList<>();

        emailHistoryEntityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }



    public Page<EmailHistoryDto> getPage(Integer page,Integer size){

        Pageable pageable = PageRequest.of(page,size);
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAll(pageable);
        List<EmailHistoryEntity> entityList = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();

        List<EmailHistoryDto> content = new LinkedList<>();

        entityList.forEach(entity -> content.add(toDto(entity)));

        return new PageImpl<>(content,pageable,totalElement);

    }

    public EmailHistoryDto toDto(EmailHistoryEntity entity) {

        EmailHistoryDto dto = new EmailHistoryDto();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setEmail(entity.getEmail());

        return dto;
    }

}
