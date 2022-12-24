package com.example.service.commentService;

import com.example.dto.entityDto.comment.CommentCreateDto;
import com.example.dto.entityDto.comment.CommentUpdateDto;
import com.example.dto.jwt.JwtDto;
import com.example.entity.ProfileEntity;
import com.example.entity.comment.CommentEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.exp.ForbiddenException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.EmailHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.RegionRepository;
import com.example.repository.comment.CommentRepository;

import com.example.service.ResourceBundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public CommentCreateDto create(CommentCreateDto dto, JwtDto jwtDto) {

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDate.now());
        entity.setReplyId(dto.getReplyId());
        entity.setProfileId(jwtDto.getId());
        entity.setArticleId(dto.getArticleId());

        commentRepository.save(entity);

        return dto;
    }


    public CommentUpdateDto update(CommentUpdateDto dto, JwtDto jwtDto, Language language) {

        Optional<CommentEntity> comment = commentRepository.findByArticleIdAndProfileId(dto.getArticleId(), jwtDto.getId());
        if (comment.isEmpty()) {
            throw new ItemNotFoundException(resourceBundleService.getMessage("item.not.found", language.name()));
        }
        CommentEntity entity = comment.get();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());


        commentRepository.save(entity);


        return dto;
    }


    public String delete(Integer id, JwtDto jwtDto) {

        Optional<CommentEntity> comment = commentRepository.findById(id);

        Optional<ProfileEntity> profile = profileRepository.findById(comment.get().getProfileId());

        if (jwtDto.getRole().equals(ProfileRole.ADMIN)) {

            if (!profile.get().getRole().equals(ProfileRole.ADMIN)) {
                comment.get().setVisible(false);
                commentRepository.save(comment.get());
                return "deleted";
            } else if (jwtDto.getId().equals(comment.get().getProfileId())) {

                comment.get().setVisible(false);
                commentRepository.save(comment.get());
                return "deleted";

            } else {
                throw new ForbiddenException("Method not allowed");
            }


        }


        if (jwtDto.getId().equals(comment.get().getProfileId())) {
            comment.get().setVisible(false);
            commentRepository.save(comment.get());
            return "deleted";
        } else {
            throw new ForbiddenException("Method not allowed");
        }

    }
}
