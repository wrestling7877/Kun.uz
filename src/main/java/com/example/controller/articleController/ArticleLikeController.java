package com.example.controller.articleController;

import com.example.service.ArticleServile.ArticleLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/like")
@Tag(name = "ArticleLikeController API", description = "Api list for ArticleLikeController")

public class ArticleLikeController {

    private final ArticleLikeService likeService;

    public ArticleLikeController(ArticleLikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/public/like")
    public ResponseEntity<?> like(@RequestParam("article_id") String article_id) {

        likeService.createLike(article_id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/public/dislike")
    public ResponseEntity<?> dislike(@RequestParam("article_id") String article_id) {

        likeService.createDislike(article_id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @DeleteMapping("/public/remove")
    public ResponseEntity<?> dislike(@RequestParam("likeId") Integer likeId,
                                     @RequestParam("article_id") String article_id) {
        likeService.removeLike(likeId, article_id);
        return ResponseEntity.ok("REMOVED");
    }
}
