package com.example.controller;

import com.example.dto.entityDto.CategoryDto;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Tag(name = "CategoryController API", description = "Api list for CategoryController")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/admin/create")
    private ResponseEntity<?> create(@RequestBody @Valid CategoryDto dto,
                                     HttpServletRequest request) {
        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(categoryService.create(dto));
    }
}
