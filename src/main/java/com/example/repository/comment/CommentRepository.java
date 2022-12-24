package com.example.repository.comment;

import com.example.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

  //  @Query("from CommentEntity c where c.articleId = ?1 and c.profileId =?2")
    Optional<CommentEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);



}
