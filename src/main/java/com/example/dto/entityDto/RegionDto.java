package com.example.dto.entityDto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {


    private Integer id;


    private String key;


    private String nameUz;


    private String nameRu;


    private String nameEn;


    private Boolean visible;


    private LocalDateTime createdDate;
}
