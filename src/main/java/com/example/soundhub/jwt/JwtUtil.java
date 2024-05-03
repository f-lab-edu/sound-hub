package com.example.soundhub.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.soundhub.presentation.dto.response.UserResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.accessTokenValidity}")
	long accessTokenValidity;

	@Value("${jwt.refreshTokenValidity}")
	long refreshTokenValidity;

	@Value("${jwt.secret}")
	private String secretKey;

	public UserResponse.tokenInfo generateTokens(Long userId) {
		String accessToken = createAccessToken(userId);
		String refreshToken = createRefreshToken(userId);

		UserResponse.tokenInfo tokenInfo = UserResponse.tokenInfo.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();

		return tokenInfo;
	}

	private String createAccessToken(Long userId) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("type", "Access");

		return Jwts.builder()
			.setClaims(claims)
			.setSubject("AccessToken")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	private String createRefreshToken(Long userId) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
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
			throw e;
		} catch (Exception e) {
			return false;
		}
	}

	public String refreshAccessToken(String refreshToken) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
		Long userId = claims.get("userId", Long.class);

		return createAccessToken(userId);
	}

	public Long extractUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		Long userId = claims.get("userId", Long.class);
		System.out.println("userId: " + userId);
		return userId;
	}
}