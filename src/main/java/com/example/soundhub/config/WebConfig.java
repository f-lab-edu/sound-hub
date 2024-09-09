package com.example.soundhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.soundhub.jwt.JwtInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/work/**"); // Apply to specific patterns
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/users/add-profile");
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/users/change-profile");
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/users/profile");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8080", "http://52.79.96.182:8080", "http://localhost:3000",
				"https://localhost:3000", "https://127.0.0.1:3000")
			.allowedMethods("GET", "POST", "PUT", "DELETE");
	}

}
