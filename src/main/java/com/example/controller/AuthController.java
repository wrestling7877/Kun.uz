package com.example.controller;

import com.example.dto.auth.AuthorizationDto;
import com.example.dto.auth.AuthorizationResponseDto;
import com.example.dto.auth.RegisterDto;
import com.example.enums.Language;
import com.example.service.AuthService;
import com.example.service.ResourceBundleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController API", description = "Api list for AuthController")
public class AuthController {
    //private final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;


    @Autowired
    private ResourceBundleService resourceBundleService;

    @PostMapping("/login")
    @Operation(summary = "Method for Authorization", description = "This method used for login")
    public ResponseEntity<AuthorizationResponseDto> login(@RequestBody AuthorizationDto dto,
                                   @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
//        log.trace(" Log trace  : " + dto);
//        log.debug("Log Debug : " + dto);
//        log.info("Log info : " + dto);
//        log.warn("Log warning : " + dto);
//        log.error("Log  error: " + dto);
        AuthorizationResponseDto responseDto = authService.authorization(dto, language);


        return ResponseEntity.ok(responseDto);
    }

//    @Operation(summary = "Method for Registration", description = "This method used for registration")
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestParam("name") String name,
//                                      @RequestParam("surname") String surname,
//                                      @RequestParam("email") String email,
//                                      @RequestParam("phone") String phone,
//                                      @RequestParam("password") String password) {
//
//        String result = authService.register(name, surname, email, phone, password);
//        return ResponseEntity.ok(result);
//    }

    @Operation(summary = "Method for Registration", description = "This method used for registration")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {

        String result = authService.register(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Method for verification", description = "This method used for verification")
    @GetMapping("/verification/email/{jtwToken}")
    public ResponseEntity<?> emailVerification(@PathVariable("jtwToken") String jwt) {
        String response = authService.verification(jwt);
        return ResponseEntity.ok(response);
    }
}
