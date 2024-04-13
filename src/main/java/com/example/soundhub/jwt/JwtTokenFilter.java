package com.example.soundhub.jwt;



import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

public class JwtTokenFilter implements Filter {

    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String accessToken = httpRequest.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
            try {
                if (jwtUtil.validateToken(accessToken)) {
                    chain.doFilter(request, response);
                    return;
                }
            } catch (ExpiredJwtException e) {
                String refreshToken = httpRequest.getHeader("Refresh");
                accessToken = jwtUtil.refreshAccessToken(refreshToken);
                if (accessToken != null) {
                    httpResponse.setHeader("Authorization", "Bearer " + accessToken);
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Unauthorized");
    }

    @Override
    public void destroy() {
    }

    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
}