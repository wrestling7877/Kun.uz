package com.example.dto.jwt;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
    private Integer id;

    private ProfileRole role;

    private String email;


    public JwtDto(Integer id, ProfileRole role, String email) {
        this.id = id;
        this.role = role;
        this.email = email;
    }


    public JwtDto() {
    }

    @Override
    public String toString() {
        return "JwtDto{" +
                "id=" + id +
                ", role=" + role +
                ", email='" + email + '\'' +
                '}';
    }
}
