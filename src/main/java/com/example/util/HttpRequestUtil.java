package com.example.util;

import com.example.dto.jwt.JwtDto;
import com.example.enums.ProfileRole;
import com.example.exp.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

    public static JwtDto getJwtDto(HttpServletRequest request,ProfileRole requiredRole) {


            Integer id = (Integer) request.getAttribute("id");
            String email = (String) request.getAttribute("email");
            ProfileRole role = (ProfileRole) request.getAttribute("role");

            if (!role.equals(requiredRole))
                throw new ForbiddenException("Method not allowed");

            return new JwtDto(id, role, email);

    }

    public static Integer getProfileId(HttpServletRequest request,ProfileRole requiredRole) {


            Integer id = (Integer) request.getAttribute("id");

            ProfileRole role = (ProfileRole) request.getAttribute("role");

            if (!role.equals(requiredRole))
                throw new ForbiddenException("Method not allowed");

            return id;

    }
}
