package com.example.service;

import com.example.dto.entityDto.profileDto.ProfileDto;
import com.example.dto.entityDto.profileDto.ProfileFilterDto;
import com.example.dto.jwt.JwtDto;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.ForbiddenException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.service.AttachService;
import com.example.service.ResourceBundleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private AttachService attachService;


    @Autowired
    private ProfileCustomRepository profileCustomRepository;


    public ProfileDto createProfile(ProfileDto profileDto) {


        Optional<ProfileEntity> emailOption = profileRepository.findByEmail(profileDto.getEmail());

        Optional<ProfileEntity> phoneOption = profileRepository.findByPhone(profileDto.getPhone());

        if (profileDto.getRole().equals(ProfileRole.ADMIN)) throw new ForbiddenException("Method not allowed");

        if (profileDto.getRole().equals(ProfileRole.USER)) throw new ForbiddenException("Method not allowed");

        if (phoneOption.isPresent()) throw new ForbiddenException("the profile already added");

        if (emailOption.isPresent()) throw new ForbiddenException("the profile already added");


        ProfileEntity profile = new ProfileEntity();

        profile.setEmail(profileDto.getEmail());

        profile.setCreatedDate(LocalDateTime.now());

        profile.setRole(profileDto.getRole());

        profile.setVisible(profileDto.getVisible());

        profile.setStatus(ProfileStatus.ACTIVE);

        profile.setSurname(profileDto.getSurname());

        profile.setPassword(profileDto.getPassword());

        profile.setPhone(profileDto.getPhone());

        profile.setName(profileDto.getName());

        System.out.println(profile);

        profileRepository.save(profile);

        profileDto.setId(profile.getId());

        return profileDto;
    }


    public ProfileDto update(ProfileDto profileDto, JwtDto jwtDto) {

        if (jwtDto.getRole().equals(ProfileRole.ADMIN)) {

            Optional<ProfileEntity> optionalProfile = profileRepository.findById(profileDto.getId());
            if (optionalProfile.isEmpty())
                throw new ItemNotFoundException("id nit found");

            ProfileEntity profile = optionalProfile.get();

            profile.setId(jwtDto.getId());
            if (!profile.getEmail().equals(jwtDto.getEmail())) {

                if (profile.getRole().equals(ProfileRole.ADMIN)) throw new ForbiddenException("Method not allowed");

                profile.setId(profileDto.getId());

            }
            profile.setPassword(profileDto.getPassword());

            profile.setCreatedDate(LocalDateTime.now());

            if (profileDto.getRole().equals(ProfileRole.ADMIN)) throw new ForbiddenException("Method not allowed");

            profile.setRole(profileDto.getRole());

            profile.setVisible(profileDto.getVisible());

            profile.setStatus(profileDto.getStatus());

            profile.setSurname(profileDto.getSurname());


            Optional<ProfileEntity> emailOption = profileRepository.findByEmail(profileDto.getEmail());
            if (emailOption.isPresent()) {

                if (emailOption.get().getEmail().equals(profileDto.getEmail())) {
                    profile.setEmail(profileDto.getEmail());
                } else {
                    throw new ForbiddenException("the email already used");

                }

            }

            profile.setEmail(profileDto.getEmail());


            Optional<ProfileEntity> phoneOption = profileRepository.findByPhone(profileDto.getPhone());
            if (phoneOption.isPresent()) {

                if (phoneOption.get().getPhone().equals(profileDto.getPhone())) {
                    profile.setPhone(profileDto.getPhone());
                } else {
                    throw new ForbiddenException("the phone already used");
                }
            }
            profile.setPhone(profileDto.getPhone());


            profile.setName(profileDto.getName());
            profileRepository.save(profile);

            profileDto.setId(profile.getId());
        } else {


            ProfileEntity profile2 = new ProfileEntity();

            profile2.setId(jwtDto.getId());


            profile2.setCreatedDate(LocalDateTime.now());

            profile2.setRole(jwtDto.getRole());

            profile2.setVisible(profileDto.getVisible());

            profile2.setStatus(ProfileStatus.ACTIVE);

            profile2.setSurname(profileDto.getSurname());

            Optional<ProfileEntity> emailOption = profileRepository.findByEmail(profileDto.getEmail());
            if (emailOption.isPresent()) {

                if (emailOption.get().getEmail().equals(profileDto.getEmail())) {
                    profile2.setEmail(profileDto.getEmail());
                } else {
                    throw new ForbiddenException("the email already used");

                }

            }

            profile2.setEmail(profileDto.getEmail());


            Optional<ProfileEntity> phoneOption = profileRepository.findByPhone(profileDto.getPhone());
            if (phoneOption.isPresent()) {

                if (phoneOption.get().getPhone().equals(profileDto.getPhone())) {
                    profile2.setPhone(profileDto.getPhone());
                } else {
                    throw new ForbiddenException("the phone already used");
                }
            }
            profile2.setPhone(profileDto.getPhone());

            profile2.setPassword(profileDto.getPassword());

            profile2.setName(profileDto.getName());
            profileRepository.save(profile2);
            profileDto.setId(profile2.getId());
        }


        return profileDto;
    }


    public Page<ProfileDto> getListPage(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        List<ProfileEntity> entityList = pageObj.getContent();
        long totalElement = pageObj.getTotalElements();

        List<ProfileDto> contentList = new LinkedList<>();
        for (ProfileEntity profile : entityList) {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setId(profile.getId());
            profileDto.setName(profile.getName());
            profileDto.setPassword(profile.getPassword());
            profileDto.setPhone(profile.getPhone());
            profileDto.setVisible(profile.getVisible());
            profileDto.setRole(profile.getRole());
            profileDto.setEmail(profile.getEmail());
            profileDto.setStatus(profile.getStatus());
            profileDto.setSurname(profile.getSurname());
            profileDto.setCreatedDate(profile.getCreatedDate());
            contentList.add(profileDto);

        }
        return new PageImpl<>(contentList, pageable, totalElement);
    }


    public String delete(Integer id) {


        Optional<ProfileEntity> profileEntity = profileRepository.findById(id);
        if (profileEntity.isEmpty()) {
            throw new ItemNotFoundException("id not found");
        }

        profileRepository.deleteById(id);


        return "Deleted was successful";
    }


    public ProfileDto toDTO(ProfileEntity entity){

        ProfileDto dto = new ProfileDto();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setRole(entity.getRole());
        dto.setSurname(entity.getSurname());
        dto.setStatus(entity.getStatus());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        dto.setImage(attachService.getById(entity.getImageId()));
        dto.setPhone(entity.getPhone());
        dto.setPrtId(entity.getPrtId());
        return dto;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            log.warn("Profile not found id = {}", id);
            throw new ItemNotFoundException(resourceBundleService.getMessage("item.not.found", Language.RU, id));
        });
    }




     public List<ProfileDto> filter(ProfileFilterDto filterDTO, int page, int size) {
        Page<ProfileEntity> list = profileCustomRepository.filterPage(filterDTO, page, size);
        for (ProfileEntity entity : list.getContent()) {
            System.out.println(entity.getId() + " " + entity.getName() + " ");
        }
        return null;
    }
}
