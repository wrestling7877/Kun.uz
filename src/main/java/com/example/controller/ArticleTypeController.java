package com.example.controller;

import com.example.dto.entityDto.article.ArticleTypeDto;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleTypeMap;
import com.example.service.ArticleTypeService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article_type")
@Tag(name = "ArticleTypeController API", description = "Api list for ArticleTypeController")

public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleService;


    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody ArticleTypeDto dto,
                                  HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        ArticleTypeDto result = articleService.create(dto);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin")
    public ResponseEntity<?> update(@RequestBody ArticleTypeDto dto,
                                    HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        ArticleTypeDto result = articleService.update(dto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        String result = articleService.delete(id);

        return ResponseEntity.ok(result);
    }


    @GetMapping("admin/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        Page<ArticleTypeDto> result = articleService.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("public/lang")
    public ResponseEntity<?> getList(@RequestParam("lang") String lang ) {


        List<ArticleTypeMap> result =articleService.getByLang(lang);
        return  ResponseEntity.ok(result);
    }
}
