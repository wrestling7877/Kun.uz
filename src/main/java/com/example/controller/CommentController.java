package com.example.controller;

import com.example.dto.auth.AuthorizationDto;
import com.example.dto.entityDto.comment.CommentCreateDto;
import com.example.dto.entityDto.comment.CommentUpdateDto;
import com.example.dto.jwt.JwtDto;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.repository.comment.CommentRepository;
import com.example.service.commentService.CommentService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Tag(name = "CommentController API", description = "Api list for CommentController")

public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/create")
    private ResponseEntity<CommentCreateDto> create(@RequestBody CommentCreateDto dto,
                                                    HttpServletRequest request) {
       JwtDto jwtDto =  HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);
        CommentCreateDto result = commentService.create(dto ,jwtDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    private ResponseEntity<CommentUpdateDto> update(@RequestBody @Valid CommentUpdateDto dto,
                                                    HttpServletRequest request,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
          JwtDto jwtDto = HttpRequestUtil.getJwtDtoForEveryone(request);
        CommentUpdateDto result = commentService.update(dto,jwtDto,language);
        return ResponseEntity.ok(result);
    }



    @DeleteMapping("/delete/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Integer id,
                                                    HttpServletRequest request) {
        JwtDto jwtDto =  HttpRequestUtil.getJwtDtoForEveryone(request);
        String result = commentService.delete(id ,jwtDto);
        return ResponseEntity.ok(result);
    }

}
