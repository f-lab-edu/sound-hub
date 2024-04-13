package com.example.soundhub.config;

import com.example.soundhub.jwt.JwtTokenFilter;
import com.example.soundhub.jwt.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();
        jwtTokenFilter.setJwtUtil(jwtUtil);

        registrationBean.setFilter(jwtTokenFilter);
        registrationBean.addUrlPatterns("/work/*"); // Secure only API endpoints
        return registrationBean;
    }
}
