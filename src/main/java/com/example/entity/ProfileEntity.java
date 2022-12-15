package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "profile")
//@Table(name = "profile",uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname"}))
public class ProfileEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column
    private String password;

    @Column
    private Boolean visible;


    @Enumerated(EnumType.STRING)
    @Column
    private ProfileStatus status;


    @Enumerated(EnumType.STRING)
    @Column
    private ProfileRole role;


    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id",updatable = false, insertable = false)
    private AttachEntity image;


    @Column
    private Integer prtId;
}
