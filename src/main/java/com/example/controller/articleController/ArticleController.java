package com.example.controller.articleController;

import com.example.dto.entityDto.article.*;
import com.example.enums.Language;
import com.example.enums.ProfileRole;

import com.example.mapper.ArticleFullInfo;
import com.example.service.ArticleServile.ArticleService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
@Tag(name = "ArticleController API", description = "Api list for ArticleController")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Operation(summary = "Method for creating", description = "This method used for creating")
    @PostMapping("/admin/create")
    public ResponseEntity<ArticleDto> create(@RequestBody @Valid ArticleCreatedDto dto
            , HttpServletRequest request) {

        Integer moderatorId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);

        ArticleDto articleDto = articleService.create(dto, moderatorId);
        return ResponseEntity.ok(articleDto);
    }

    @Operation(summary = "Method for updating", description = "This method used for updating")
    @PutMapping("/admin/update")
    public ResponseEntity<ArticleDto> update(@RequestBody ArticleCreatedDto dto, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);

        ArticleDto articleDto = articleService.update(dto);
        return ResponseEntity.ok(articleDto);
    }

    @Operation(summary = "Method for deleting", description = "This method used for deleting")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);


        return ResponseEntity.ok(articleService.delete(id));
    }


    @GetMapping("/public/get_last8")
    public ResponseEntity<?> getLast8(@RequestBody List<String> idList) {
        List<ArticleShortInfoDTO> result = articleService.getLast8(idList);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/admin/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          HttpServletRequest request) {
        Integer moderatorId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        Boolean result = articleService.changeStatus(id, moderatorId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/get_last5/{typeId}")
    public ResponseEntity<?> getLast5(@PathVariable("typeId") Integer typeId) {

        List<ArticleShortInfoDTO> result = articleService.getLast5(typeId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/get_last3/{typeId}")
    public ResponseEntity<?> getLast3(@PathVariable("typeId") Integer typeId) {

        List<ArticleShortInfoDTO> result = articleService.getLast3(typeId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/lang")
    public ResponseEntity<?> getByLang(@RequestParam("lang") String lang,
                                       @RequestParam("id") String id) {

        List<ArticleFullInfo> result = articleService.getByLang(lang, id);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/last4")
    public ResponseEntity<?> getLast4() {

        return ResponseEntity.ok(articleService.getLast4());
    }

    @GetMapping("/public/last5")
    public ResponseEntity<?> getLast5ByRegionKey(@RequestParam("key") String key,
                                                 @RequestParam("typeId") Integer typeId) {
        List<ArticleShortInfoDTO> result = articleService.getLast5ByRegion(key, typeId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/list_page")
    public ResponseEntity<?> getLast5ByRegionKey(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam("key") String key) {
        Page<ArticleShortInfoDTO> result = articleService.getPageByRegionKey(key, page, size);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/listByCategoryKey")
    public ResponseEntity<?> getLast5ByCategoryKey(@RequestParam("key") String key) {

        List<ArticleShortInfoDTO> result = articleService.getListByCategory(key);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/list_page_category")
    public ResponseEntity<?> getListByCategoryKey(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size,
                                                  @RequestParam("key") String key) {
        Page<ArticleShortInfoDTO> result = articleService.getPageByCategoryKey(key, page, size);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/public/view_count")
    public ResponseEntity<?> viewCount(@RequestParam("articleId") String articleId,
                                       @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {

        Integer counts = articleService.createViewCount(articleId, language);

        return ResponseEntity.ok(counts);
    }

    @PostMapping("/public/shared_count")
    public ResponseEntity<?> sharedCount(@RequestParam("articleId") String articleId,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {

        Integer counts = articleService.createSharedCount(articleId, language);

        return ResponseEntity.ok(counts);
    }

    @Operation(summary = "Method for getFilterShortInfoPage", description = "This method used for getFilterShortInfoPage")
    @PostMapping("/admin/filterPage")
    public ResponseEntity<?> getFilterShortInfoPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestBody ArticleFilterDto dto,
                                           HttpServletRequest request) {
        HttpRequestUtil.getJwtDto(request,ProfileRole.PUBLISHER);
        Page<ArticleShortInfoDTO> result = articleService.filterShortInfo(dto, page, size);
        return ResponseEntity.ok(result);
    }


//    @Operation(summary = "Method for getFilterFullInfoPage", description = "This method used for getFilterFullInfoPage")
//    @PostMapping("/admin/fullFilterPage")
//    public ResponseEntity<?> getFilterFullInfoPage(@RequestParam(value = "page", defaultValue = "0") int page,
//                                           @RequestParam(value = "size", defaultValue = "5") int size,
//                                           @RequestBody ArticleFilterDto dto,
//                                           HttpServletRequest request) {
//        HttpRequestUtil.getJwtDto(request,ProfileRole.PUBLISHER);
//        Page<ArticleFilterFullInfoDto> result = articleService.filterFullInfo(dto, page, size);
//        return ResponseEntity.ok(result);
//    }

}
