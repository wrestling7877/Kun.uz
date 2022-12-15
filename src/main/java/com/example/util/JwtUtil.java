package com.example.util;

import com.example.dto.jwt.JwtDto;
import com.example.enums.ProfileRole;
import com.example.exp.JwtNotValidException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private static final String secretKey = "secretKey!123";//
    public static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day // tokenLiveTime //
    // public static final int tokenLiveTime = 2000 * 60; // 1-day // tokenLiveTime //

    public static String encode(String profileEmail, Integer profileId, ProfileRole role) {

        JwtBuilder jwtBuilder = Jwts.builder();//
        jwtBuilder.setIssuedAt(new Date());  // when did jwt creat
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey); // HS512 algorithm type

        jwtBuilder.claim("id", profileId); // for input key value
        jwtBuilder.claim("email", profileEmail); // for input key value
        jwtBuilder.claim("role", role);    // for input key value
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Kun.uz"); // created by

        return jwtBuilder.compact(); // to return token
    }


    public static String encode(Integer profileId) {

        JwtBuilder jwtBuilder = Jwts.builder();//
        jwtBuilder.setIssuedAt(new Date());  // when did jwt creat
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey); // HS512 algorithm type

        jwtBuilder.claim("id", profileId); // for input key value
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (1000 * 3600 * 1)));
        jwtBuilder.setIssuer("Kun.uz"); // created by

        return jwtBuilder.compact(); // to return token
    }

    public static JwtDto decode(String token) {


        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");
        String email = (String) claims.get("email");

        ProfileRole profileRole = ProfileRole.valueOf(role);

        return new JwtDto(id, profileRole, email);


    }

    public static Integer decodeForEmailVerification(String jwt) {


        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);

        Claims claims = claimsJws.getBody();

        Integer id = (Integer) claims.get("id");



        return id;
    }
}
