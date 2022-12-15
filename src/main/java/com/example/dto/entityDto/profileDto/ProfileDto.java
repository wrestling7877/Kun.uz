package com.example.dto.entityDto.profileDto;

import com.example.dto.entityDto.AttachDto;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    private Integer id;

    @Size(min = 2, message = "name should not be less than 2")
    private String name;

    @Size(min = 2, message = "name should not be less than 2")

    private String surname;

    @Email(message = "Email should be valid")
    private String email;

    // @Pattern(regexp = "s")  // I must learn this pattern it is very important
    private String phone;

    @NotBlank
    private String password;


    private Boolean visible;


    private ProfileStatus status;


    private ProfileRole role;


    private LocalDateTime createdDate;


    private AttachDto image;


    private Integer prtId;


}
