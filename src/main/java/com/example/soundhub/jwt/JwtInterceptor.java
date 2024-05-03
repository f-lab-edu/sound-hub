package com.example.soundhub.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String accessToken = request.getHeader("Authorization");
		if (accessToken != null && accessToken.startsWith("Bearer ")) {
			accessToken = accessToken.substring(7);
			try {
				if (jwtUtil.validateToken(accessToken)) {
					Long userId = jwtUtil.extractUserId(accessToken);
					request.setAttribute("userId", userId);
					return true; // continue the chain
				}
			} catch (ExpiredJwtException e) {
				String refreshToken = request.getHeader("Refresh");
				accessToken = jwtUtil.refreshAccessToken(refreshToken);
				if (accessToken != null) {
					response.setHeader("Authorization", "Bearer " + accessToken);
					return true; // continue the chain
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Unauthorized");
		return false; // break the chain
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		// Post-handle logic
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		// Cleanup logic
	}
}