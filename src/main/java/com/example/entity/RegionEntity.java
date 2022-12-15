package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "region")
public class RegionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String key;

    @Column
    private String nameUz;

    @Column
    private String nameRu;

    @Column
    private String nameEn;

    @Column
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
