package com.example.dto.entityDto.profileDto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class ProfileFilterDto {

    private String name;
    private String surname;
    private String phone;
    private ProfileStatus status;
    private List<ProfileRole> role;
    private LocalDate fromDate;
    private LocalDate toDate;

}
