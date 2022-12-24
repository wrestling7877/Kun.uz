package com.example.service.ArticleServile;

import com.example.entity.article.ArticleEntity;
import com.example.entity.article.ArticleLikeEntity;
import com.example.enums.LikeEnum;
import com.example.repository.article.ArticleLikeRepository;
import com.example.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ArticleLikeService {


    private final ArticleLikeRepository likeRepository;

    private final ArticleRepository articleRepository;

    public ArticleLikeService(ArticleLikeRepository likeRepository, ArticleRepository articleRepository) {
        this.likeRepository = likeRepository;
        this.articleRepository = articleRepository;
    }


    public void createLike(String articleId) {


        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setLike_dislike(LikeEnum.LIKE);
        likeRepository.save(entity);

        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        if (article.get().getLikeCount() != null) {
            Integer count = article.get().getLikeCount() + 1;
            article.get().setLikeCount(count);
            articleRepository.save(article.get());

            return;
        }

        article.get().setLikeCount(1);
        articleRepository.save(article.get());
    }

    public void createDislike(String articleId) {


        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setLike_dislike(LikeEnum.DISLIKE);
        likeRepository.save(entity);
    }

    public void removeLike(Integer likeId, String articleId) {
        ArticleLikeEntity articleLikeEntity = likeRepository.findByIdAndArticleId(likeId, articleId);
        if (articleLikeEntity != null) {
            likeRepository.deleteById(likeId);
        }

    }
}
