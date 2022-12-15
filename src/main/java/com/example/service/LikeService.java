package com.example.service;

import com.example.entity.LikeEntity;
import com.example.enums.LikeEnum;
import com.example.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {


    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }


    public void createLikeDislike(String like_dislike, String articleId) {


       LikeEntity entity = new LikeEntity();
        entity.setArticleId(articleId);
        entity.setLike_dislike(LikeEnum.valueOf(like_dislike));
        likeRepository.save(entity);
    }

    public void removeLike(Integer likeId, String like_dislike, String articleId) {
        LikeEnum likeEnum = LikeEnum.valueOf(like_dislike);

        Optional<LikeEntity> likeEntity = likeRepository.findById(likeId);
        LikeEntity like = likeEntity.get();

        if (likeEntity.get().getLike_dislike().equals(LikeEnum.LIKE) && likeEnum.equals(LikeEnum.LIKE)) {
            likeRepository.deleteById(likeId);
        }
        // like.setLike_dislike();


    }
}
