package com.example.soundhub.jwt;

import com.example.soundhub.presentation.dto.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {
    @Value("${ACCESS_TOKEN}")
    long accessTokenValidity;

    @Value("${REFRESH_TOKEN}")
    long refreshTokenValidity;

    @Value("${jwt.secret}")
    private String secretKey;

    public UserResponse.tokenInfo generateTokens(String name) {
        String accessToken = createAccessToken(name);
        String refreshToken = createRefreshToken(name);

        UserResponse.tokenInfo tokenInfo = UserResponse.tokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return tokenInfo;
    }


    private String createAccessToken(String name) {
        Claims claims = Jwts.claims();
        claims.put("userName", name);
        claims.put("type", "Access");

        return Jwts.builder().
                setClaims(claims)
                .setSubject("AccessToken")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    private String createRefreshToken(String name) {
        Claims claims = Jwts.claims();
        claims.put("userName", name);
        claims.put("type", "Refresh");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("RefreshToken")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw e; // Propagate the exception to handle in the filter
        } catch (Exception e) {
            return false;
        }
    }


    public String refreshAccessToken(String refreshToken) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();

        String userName = claims.get("userName", String.class);

        return createAccessToken(userName);
    }

}