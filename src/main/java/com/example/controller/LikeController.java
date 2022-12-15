package com.example.controller;

import com.example.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/like")
@Tag(name = "LikeController API", description = "Api list for LikeController")

public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @PostMapping("/public/like_dislike")
    public ResponseEntity<?> like_dislike(@RequestParam("like_dislike") String like_dislike,
                                          @RequestParam("article_id") String article_id) {
        likeService.createLikeDislike(like_dislike, article_id);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
