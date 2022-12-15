package com.example.controller;

import com.example.dto.entityDto.RegionDto;
import com.example.dto.jwt.JwtDto;
import com.example.enums.ProfileRole;
import com.example.mapper.RegionMap;
import com.example.service.RegionService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
@Tag(name = "RegionController API", description = "Api list for RegionController")

public class RegionController {

    @Autowired
    private RegionService regionService;


    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody RegionDto dto,
                                  HttpServletRequest request) {

         HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        RegionDto result = regionService.create(dto);

        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody RegionDto dto,
                                    HttpServletRequest request) {

       HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        RegionDto result = regionService.update(dto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        String result = regionService.delete(id);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        Page<RegionDto> result = regionService.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/lang")
    public ResponseEntity<?> getList(@RequestParam("lang") String lang,
                                     HttpServletRequest request) {

       HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        List<RegionMap> result =regionService.getByLang(lang);
        return  ResponseEntity.ok(result);
    }
}
