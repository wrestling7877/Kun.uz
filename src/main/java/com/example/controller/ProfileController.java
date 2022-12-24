package com.example.controller;

import com.example.dto.entityDto.profileDto.ProfileDto;
import com.example.dto.entityDto.profileDto.ProfileFilterDto;
import com.example.dto.jwt.JwtDto;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@Tag(name = "ProfileService API", description = "Api list for ProfileService")

public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/admin")
    public ResponseEntity<?> save(@RequestBody ProfileDto profileDto,
                                  HttpServletRequest request) {
        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);
        ProfileDto profile = profileService.createProfile(profileDto);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/public")
    public ResponseEntity<?> update(@RequestBody ProfileDto profileDto,
                                    HttpServletRequest request) {

        JwtDto jwtDto = HttpRequestUtil.getJwtDtoForEveryone(request);

        ProfileDto response = profileService.update(profileDto, jwtDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/list")
    public ResponseEntity<?> getList(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);

        Page<ProfileDto> result = profileService.getListPage(page, size);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        HttpRequestUtil.getJwtDto(request, ProfileRole.ADMIN);


        String result = profileService.delete(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProfileDto>> filter(@RequestBody ProfileFilterDto filterDTO,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        List<ProfileDto> profile = profileService.filter(filterDTO, page, size);
        return ResponseEntity.ok(profile);
    }


}
