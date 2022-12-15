package com.example.service;

import com.example.dto.auth.AuthorizationDto;
import com.example.dto.auth.AuthorizationResponseDto;
import com.example.dto.auth.RegisterDto;
import com.example.dto.entityDto.EmailHistoryDto;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AuthorizationFailedException;
import com.example.exp.EmailAlreadyExistsException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.Md5Util;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private MailService mailService;

    @Autowired
    private EmailHistoryService emailHistoryService;

    public AuthorizationResponseDto authorization(AuthorizationDto dto, Language language) {


        Optional<ProfileEntity> optionalProfile = profileRepository.findByPhoneAndPassword(dto.getPhone(), dto.getPassword());

        if (optionalProfile.isEmpty()) {
            throw new AuthorizationFailedException(resourceBundleService.getMessage("credential.wrong", language.name()));
        }

        if (!optionalProfile.get().getStatus().equals(ProfileStatus.ACTIVE))
            throw new AuthorizationFailedException("Wrong status. Please connect with admin.");

        ProfileEntity profile = optionalProfile.get();

        AuthorizationResponseDto responseDto = new AuthorizationResponseDto();
        responseDto.setName(profile.getName());
        responseDto.setSurname(profile.getSurname());
        responseDto.setRole(profile.getRole());


        responseDto.setToken(JwtUtil.encode(profile.getEmail(), profile.getId(), profile.getRole()));

        return responseDto;
    }


    public String register(RegisterDto dto) {


        Optional<ProfileEntity> emailOption = profileRepository.findByEmail(dto.getEmail());

        if (emailOption.isPresent()) {
            if (emailOption.get().getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRepository.delete(emailOption.get());
            } else {
                throw new EmailAlreadyExistsException("Email already exists");
            }

        }


        ProfileEntity profile = new ProfileEntity();
        profile.setPhone(dto.getPhone());
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail());
        profile.setRole(ProfileRole.USER);
        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profile.setPassword(Md5Util.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setSurname(dto.getSurname());
        profileRepository.save(profile);



        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                EmailHistoryDto emailHistoryDto = new EmailHistoryDto();
                StringBuilder sb = new StringBuilder();
                String message ="Salom Mazgi qalaysan";
                sb.append("<h1 style=\"text-align: center; background-color: indianred; color: white\">"+message+"</h1>");
                String link = String.format("<a href=\"http://localhost:8080/auth/verification/email/%s\"> Click there</a>", JwtUtil.encode(profile.getId()));
                sb.append(link);
                mailService.sendEmailMine(dto.getEmail(), "Complete Registration", sb.toString());

                emailHistoryDto.setEmail(dto.getEmail());
                emailHistoryDto.setMessage(message);
                emailHistoryDto.setCreatedDate(LocalDateTime.now());
                emailHistoryService.create(emailHistoryDto);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();



        return "Email ga link ketdi.";


    }

    public String verification(String jwt) {

        Integer id;
        try {
            id = JwtUtil.decodeForEmailVerification(jwt);
        } catch (JwtException e) {
            return "Verification failed";
        }

        Optional<ProfileEntity> exists = profileRepository.findById(id);
        if (exists.isEmpty()) {
            return "Verification failed";
        }
        ProfileEntity entity = exists.get();

        if (!entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
            return "Verification failed";
        }

        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);

        return "Verification success";
    }
}

