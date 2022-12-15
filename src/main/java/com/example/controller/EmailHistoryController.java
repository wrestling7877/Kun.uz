package com.example.controller;

import com.example.dto.entityDto.EmailHistoryDto;
import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.HttpRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/emailHistoryController")
@Tag(name = "EmailHistoryController API", description = "Api list for EmailHistoryController")

public class EmailHistoryController {


    @Autowired
    private EmailHistoryService emailHistoryService;


    @GetMapping("/public/get_email")
    public ResponseEntity<?> getByEmail(@RequestParam("email") String email) {

        List<EmailHistoryDto> result = emailHistoryService.getByEmail(email);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/public/get_date")
    public ResponseEntity<?> getByCreatedDate(@RequestParam("createdDate") String createdDate) {
      //  LocalDateTime createdDate = LocalDateTime.parse(date);
        List<EmailHistoryDto> result = emailHistoryService.getByCreatedDate(createdDate);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/admin/get_page")
    public ResponseEntity<?> getPage(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        Page<EmailHistoryDto> result = emailHistoryService.getPage(page,size);
        return ResponseEntity.ok(result);
    }
}
