package com.example.soundhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "SoundHub API Docs", description = "Swagger API 문서", version = "v1"))
@Configuration
public class SwaggerConfig {
	private static final String BEARER_TOKEN_PREFIX = "Bearer";

	@Bean
	public OpenAPI openAPI() {
		String securityJwtName = "JWT";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
		Components components = new Components().addSecuritySchemes(securityJwtName,
			new SecurityScheme().name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TOKEN_PREFIX)
				.bearerFormat(securityJwtName));

		return new OpenAPI().addSecurityItem(securityRequirement).components(components);
	}

}
