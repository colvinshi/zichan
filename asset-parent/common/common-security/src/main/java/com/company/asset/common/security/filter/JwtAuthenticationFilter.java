package com.company.asset.common.security.filter;

import com.company.asset.common.core.constant.GlobalConstants;
import com.company.asset.common.core.result.Result;
import com.company.asset.common.security.dto.JwtPayload;
import com.company.asset.common.security.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT authentication filter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader(GlobalConstants.TOKEN_HEADER);
        
        try {
            if (authHeader != null && authHeader.startsWith(GlobalConstants.TOKEN_PREFIX)) {
                String token = authHeader.substring(GlobalConstants.TOKEN_PREFIX.length());
                
                if (jwtService.validateToken(token)) {
                    JwtPayload payload = jwtService.parseToken(token);
                    
                    // Create authorities from roles
                    List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                    if (payload.getRoles() != null && !payload.getRoles().isEmpty()) {
                        authorities = Arrays.stream(payload.getRoles().split(","))
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                                .collect(Collectors.toList());
                    }
                    
                    // Set authentication
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(payload, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    log.debug("Authentication set for user: {}", payload.getUsername());
                }
            }
        } catch (Exception e) {
            log.warn("JWT authentication failed: {}", e.getMessage());
            // Continue without authentication for public endpoints
        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * Write error response
     */
    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        Result<?> result = Result.error(code, message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
